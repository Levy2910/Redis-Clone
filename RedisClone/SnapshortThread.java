package RedisClone;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.time.Instant;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class SnapshortThread extends Thread {
    private final StoreRedis storeRedis;
    private volatile boolean running = true;

    public SnapshortThread(StoreRedis storeRedis) {
        this.storeRedis = storeRedis;
    }

    @Override
    public void run() {
        while (running) {
            try {
                long currentTimeSeconds = Instant.now().getEpochSecond();
                String fileName = "snapshot_" + currentTimeSeconds + ".aof";

                try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
                    Set<Map.Entry<String, String>> entries = storeRedis.getAllValuesFromCurrentStore();

                    for (var entry : entries) {
                        String key = entry.getKey();
                        writer.write("SET " + key + " " + entry.getValue() + "\n");

                        if (storeRedis.isKeyInExpiredMap(key)) {
                            writer.write("EXPIRE " + key + " " + storeRedis.checkTTL(key) + "\n");
                        }
                    }
                }


                Thread.sleep(5* 60 * 1000);
            } catch (InterruptedException e) {
                System.out.println("Snapshot thread interrupted.");
                break;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        System.out.println("Snapshot thread has stopped.");
    }

    public void shutdown() {
        running = false;
        this.interrupt(); // Wake up the thread if it's sleeping
    }
}
