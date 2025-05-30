private static final int BATCH_SIZE = 50;
private final DataSource dataSource;

private static final String UPDATE_SQL =
    "UPDATE symbols SET price = ?, updated_time = CURRENT TIMESTAMP WHERE symbol = ?";

private static final String INSERT_SQL =
    "INSERT INTO symbols (symbol, price, updated_time) " +
    "SELECT ?, ?, CURRENT TIMESTAMP " +
    "WHERE NOT EXISTS (SELECT 1 FROM symbols WHERE symbol = ?)";

public SymbolBatchUpdater(DataSource dataSource) {
    this.dataSource = dataSource;
}

public void batchUpsertInChunks(List<SymbolData> symbols) {
    if (symbols.isEmpty()) return;

    try (Connection conn = dataSource.getConnection()) {
        conn.setAutoCommit(false);

        // Step 1: Preload existing prices for all symbols in one query
        Map<String, Double> existingPrices = preloadExistingPrices(conn, symbols);

        try (PreparedStatement updatePs = conn.prepareStatement(UPDATE_SQL);
             PreparedStatement insertPs = conn.prepareStatement(INSERT_SQL)) {

            int count = 0;

            for (int i = 0; i < symbols.size(); i++) {
                SymbolData symbol = symbols.get(i);
                double price = symbol.getPrice();

                if (price == 0.0) {
                    Double existing = existingPrices.get(symbol.getSymbol());
                    if (existing != null) {
                        price = existing;
                    }
                }

                updatePs.setDouble(1, price);
                updatePs.setString(2, symbol.getSymbol());
                updatePs.addBatch();

                insertPs.setString(1, symbol.getSymbol());
                insertPs.setDouble(2, price);
                insertPs.setString(3, symbol.getSymbol());
                insertPs.addBatch();

                count++;

                if (count % BATCH_SIZE == 0 || i == symbols.size() - 1) {
                    updatePs.executeBatch();
                    insertPs.executeBatch();
                    conn.commit();
                    updatePs.clearBatch();
                    insertPs.clearBatch();
                    count = 0;
                }
            }

        } catch (SQLException e) {
            conn.rollback();
            e.printStackTrace();
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }
}

private Map<String, Double> preloadExistingPrices(Connection conn, List<SymbolData> symbols) throws SQLException {
    Map<String, Double> map = new HashMap<>();
    Set<String> uniqueSymbols = symbols.stream()
                                       .map(SymbolData::getSymbol)
                                       .collect(Collectors.toSet());

    if (uniqueSymbols.isEmpty()) return map;

    String placeholders = uniqueSymbols.stream()
                                       .map(s -> "?")
                                       .collect(Collectors.joining(","));
    String sql = "SELECT symbol, price FROM symbols WHERE symbol IN (" + placeholders + ")";

    try (PreparedStatement ps = conn.prepareStatement(sql)) {
        int i = 1;
        for (String sym : uniqueSymbols) {
            ps.setString(i++, sym);
        }

        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                map.put(rs.getString("symbol"), rs.getDouble("price"));
            }
        }
    }

    return map;
}
