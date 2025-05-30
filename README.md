 String sql = "MERGE INTO symbols t " +
                 "USING (SELECT ? AS symbol, ? AS price FROM dual) s " +
                 "ON (t.symbol = s.symbol) " +
                 "WHEN MATCHED THEN UPDATE SET t.price = s.price, t.updated_time = SYSTIMESTAMP " +
                 "WHEN NOT MATCHED THEN INSERT (symbol, price, updated_time) " +
                 "VALUES (s.symbol, s.price, SYSTIMESTAMP)";
