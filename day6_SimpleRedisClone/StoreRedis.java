package day6_SimpleRedisClone;

import java.util.HashMap;

public class StoreRedis {
    private HashMap<String, String> store;

    public StoreRedis(){
        this.store = new HashMap<>();
    }

    public void set(String key, String value){
        store.put(key, value);
    }
    public String get(String key){
        if (!store.containsKey(key)){
            return null;
        }
        return store.get(key);
    }
    public void del(String key){
        if (!store.containsKey(key)){
            return;
        }
        store.remove(key);
    }
}
