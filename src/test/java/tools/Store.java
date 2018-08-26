package tools;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class Store {
    private static Map<String, String> store = new HashMap<>();
    private Store(){}

    private static class StoreHolder{
        private final static  Store instanse = new Store();
    }

    public static Store getStore(){
        return StoreHolder.instanse;
    }

    public void put(String key, String value){
        store.put(key, value);
    }

    public String get(String key){
        return store.get(key);
    }

    public String safeGet(String key){
        return Optional.ofNullable(store.get(key)).orElse("NOT FOUND");
    }
}
