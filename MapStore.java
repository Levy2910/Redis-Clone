import java.util.HashMap;

public class MapStore {
    private HashMap<String, String> store;

    public MapStore(){
        this.store = new HashMap<>();
    }

    public void set(String key, String value){
        if (store.containsKey(key)){
            return;
        }
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

