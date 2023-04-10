package pt.ua.deti.tqs.airquality.model;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class Storage<Item, Entry> {
     private final long timestamp;
     private final Map<Item, Entry> cache;
     private final Map<Item, Long> cacheTimesToLive;

     public Storage() {
            this.timestamp = (long) 100 * 1000; // 100 seconds
            this.cache = new HashMap<>();
            this.cacheTimesToLive = new HashMap<>();
        }

    public Storage(long max_time) {
        this.timestamp = max_time;
        cache = new HashMap<>();
        cacheTimesToLive = new HashMap<>();
    }

    public Entry get(Item item) {
        if (cacheTimesToLive.containsKey(item)) {
            if (cacheTimesToLive.get(item) > System.currentTimeMillis()) {
                return cache.get(item);
            } else {
                cacheTimesToLive.remove(item);
                cache.remove(item);
            }
        }
        return null;
    }

    public void add(Item item, Entry entry) {
        cache.put(item, entry);
        cacheTimesToLive.put(item, System.currentTimeMillis() + timestamp);
    }

}
