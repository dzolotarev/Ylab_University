package lesson3.datedmap;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author Denis Zolotarev
 */
public class DatedMapImpl implements DatedMap {
    private final Map<String, Entry> map = new HashMap<>();

    @Override
    public void put(String key, String value) {
        map.put(key, new Entry(value));
    }

    @Override
    public String get(String key) {
        Entry entry = map.get(key);
        if (entry == null) {
            return null;
        }
        return entry.getValue();
    }

    @Override
    public boolean containsKey(String key) {
        return map.containsKey(key);
    }

    @Override
    public void remove(String key) {
        map.remove(key);
    }

    @Override
    public Set<String> keySet() {
        return map.keySet();
    }

    @Override
    public Date getKeyLastInsertionDate(String key) {
        Entry entry = map.get(key);
        if (entry == null) {
            return null;
        }
        return entry.getDate();
    }

    private static class Entry {
        private final String value;
        private final long date;

        public Entry(String value) {
            this.value = value;
            this.date = System.currentTimeMillis();
        }

        public String getValue() {
            return value;
        }

        public Date getDate() {
            return new Date(date);
        }
    }
}
