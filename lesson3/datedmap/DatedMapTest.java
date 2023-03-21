package lesson3.datedmap;

/**
 * @author Denis Zolotarev
 */
public class DatedMapTest {
    public static void main(String[] args) throws InterruptedException {
        DatedMap map = new DatedMapImpl();
        map.put("one", "This is value for key \"one\"");
        map.put("two", "This is value for key \"two\"");
        System.out.println(map.get("one") + "| date: " + map.getKeyLastInsertionDate("one"));
        System.out.println(map.get("two") + "| date: " + map.getKeyLastInsertionDate("two"));
        Thread.sleep(2000);
        map.put("two", "This is updated value for key \"two\"");
        System.out.println(map.get("two") + "| date: " + map.getKeyLastInsertionDate("two"));
        System.out.println(map.containsKey("two"));

        System.out.println(map.containsKey("three"));
        System.out.println(map.getKeyLastInsertionDate("three"));

        System.out.println(map.keySet());
        map.remove("two");
        System.out.println(map.get("two"));
        System.out.println(map.getKeyLastInsertionDate("two"));
    }
}
