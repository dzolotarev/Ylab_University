package lesson3.datedmap;

import java.util.Date;
import java.util.Set;

/**
 * @author Denis Zolotarev
 */
public interface DatedMap {
    void put(String key, String value);

    String get(String key);

    boolean containsKey(String key);

    void remove(String key);

    Set<String> keySet();

    Date getKeyLastInsertionDate(String key);
}
