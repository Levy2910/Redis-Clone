package day6_SimpleRedisClone;

import java.time.Instant;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class ExpiryThread extends Thread {
    private final StoreRedis storeRedis;
    private volatile boolean running = true;
    private final AOFHandler aofHandler;
    public ExpiryThread(StoreRedis storeRedis, AOFHandler aofHandler){
        this.storeRedis = storeRedis;
        this.aofHandler = aofHandler;
    }
    @Override
    public void run() {
        while (running){
            try{
                long currTime = Instant.now().getEpochSecond();
              Set<Map.Entry<String, Long>> keysInExpiredMap =  storeRedis.getAllValuesFromExpiredMap();
              for (Map.Entry<String, Long> entry : keysInExpiredMap){
                  String key = entry.getKey();
                  if (storeRedis.isExpiredKey(key, currTime)){
                      storeRedis.del(key);
                      storeRedis.delFromExpiryMap(key);
                      aofHandler.append("del "+ key );
                  }
              }
                storeRedis.cleanExpiredKey();
                Thread.sleep(1000);
            }catch (Exception e){
                break;
            }
        }
        System.out.println("Expiry thread stopped.");
    }
    public void shutdown() {
        running = false;
        this.interrupt();
    }

}
