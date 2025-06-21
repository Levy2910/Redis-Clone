package day6_SimpleRedisClone;

import java.time.Instant;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

public class StoreRedis {
    private ConcurrentHashMap<String, String> store;
    private HashMap<String, Long> expiryMap;

    public StoreRedis(){
        this.store = new ConcurrentHashMap<>();
        this.expiryMap = new HashMap<>();
    }

    public void set(String key, String value){
        store.put(key, value);
    }
    public String get(String key){
        if (!store.containsKey(key)){
            return null;
        }
        if (!expiryMap.containsKey(key)){
            return store.get(key);
        }
        return checkExpiredOrNot(key);
    }

    private String checkExpiredOrNot(String key) {
        long currentTime = Instant.now().getEpochSecond();
        long timeLeft = expiryMap.get(key) - currentTime;
        if (timeLeft > 0){
            return store.get(key);
        }else{
            store.remove(key);
            return null;
        }
    }

    public void del(String key){
        if (!store.containsKey(key)){
            return;
        }
        store.remove(key);
    }
    public boolean exist(String key){
        if (store.containsKey(key)){
            return true;
        }
        return false;
    }
    public Integer incr(String key) throws Exception {
        if (!store.containsKey(key)){
            store.put(key, "0");
        }
        String value = store.get(key);
        if (!isInteger(value)){
            throw new Exception("Value is not an integer");
        }
        int intValue = Integer.parseInt(value);
        intValue++;
        store.put(key, String.valueOf(intValue));
        return intValue;
    }
    public Integer decr(String key) throws Exception {
        if (!store.containsKey(key)){
            store.put(key, "0");
        }
        String value = store.get(key);
        if (!isInteger(value)){
            throw new Exception("Value is not an integer");
        }
        int intValue = Integer.parseInt(value);
        intValue--;
        store.put(key, String.valueOf(intValue));
        return intValue;
    }


    public String mSet(HashMap<String, String> map) {
        StringBuilder result = new StringBuilder();
        for (String key : map.keySet()) {
            set(key, map.get(key));
            result.append("OK");
        }
        return result.toString();
    }
    public String mGet(String[] keys) {
        StringBuilder result = new StringBuilder();
        for (String key : keys) {
            if (store.containsKey(key)) {
                result.append(store.get(key)).append(", ");
            } else {
                result.append("null, ");
            }
        }
        return result.toString();
    }

    private boolean isInteger(String s) {
        if (s == null || s.isEmpty()) {
            return false;
        }
        try {
            Integer.parseInt(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    public void deleteAll(){
        for (String key : store.keySet()){
            store.remove(key);
        }
    }
    public void setExpiryDate(String key, long extraTime){
        long currentTimeSeconds = Instant.now().getEpochSecond();
        expiryMap.put(key, currentTimeSeconds + extraTime);
    }
    public long checkTTL(String key){
        if (!store.containsKey(key)){
            return -2;
        }if (!expiryMap.containsKey(key)){
            return -1;
        }
        long currentTimeSeconds = Instant.now().getEpochSecond();
        return expiryMap.get(key) - currentTimeSeconds;
    }
}


