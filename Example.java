import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class PriceTaskManager {

    private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
    private ScheduledFuture<?> scheduledTask;
    private final PriceDataService priceDataService;
    private final AtomicBoolean isRunning = new AtomicBoolean(false);

    // Full symbol list (190 symbols)
    private final List<String> allSymbols;

    public PriceTaskManager(PriceDataService priceDataService, List<String> allSymbols) {
        this.priceDataService = priceDataService;
        this.allSymbols = allSymbols;
    }

    public synchronized void start() {
        if (isRunning.get()) return;

        Runnable task = () -> {
            try {
                fetchPricesConcurrently();
            } catch (Exception e) {
                e.printStackTrace();
            }
        };

        scheduledTask = scheduler.scheduleWithFixedDelay(task, 0, 5, TimeUnit.SECONDS);
        isRunning.set(true);
    }

    public synchronized void stop() {
        if (scheduledTask != null && !scheduledTask.isCancelled()) {
            scheduledTask.cancel(true);
        }
        isRunning.set(false);
    }

    public synchronized void restart() {
        stop();
        start();
    }

    private void fetchPricesConcurrently() {
        List<List<String>> partitions = partitionSymbols(allSymbols, 100);

        List<CompletableFuture<Void>> futures = new ArrayList<>();
        for (List<String> batch : partitions) {
            futures.add(CompletableFuture.runAsync(() -> {
                priceDataService.fetchMarketPrice(batch);
            }));
        }

        // Wait for all to complete (optional)
        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
    }

    private List<List<String>> partitionSymbols(List<String> symbols, int maxSize) {
        List<List<String>> partitions = new ArrayList<>();
        for (int i = 0; i < symbols.size(); i += maxSize) {
            partitions.add(symbols.subList(i, Math.min(i + maxSize, symbols.size())));
        }
        return partitions;
    }

    public boolean isRunning() {
        return isRunning.get();
    }
}
