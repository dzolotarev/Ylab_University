package io.ylab.intensive.lesson04.persistentmap;

import io.ylab.intensive.lesson04.DbUtil;

import javax.sql.DataSource;
import java.sql.SQLException;

public class PersistenceMapTest {
    public static void main(String[] args) throws SQLException {
        DataSource dataSource = initDb();
        PersistentMap persistentMap = new PersistentMapImpl(dataSource);
        // Написать код демонстрации работы
        persistentMap.init("table");
        persistentMap.put("4", "2x2");
        persistentMap.put("16", "4x4");
        persistentMap.put("9", "3x3");
        System.out.println(persistentMap.get("16"));
        System.out.println(persistentMap.containsKey("16"));
        persistentMap.remove("16");
        System.out.println(persistentMap.get("16"));
        System.out.println(persistentMap.containsKey("16"));
        persistentMap.clear();
        System.out.println(persistentMap.getKeys().toString());

        PersistentMap persistentMap1 = new PersistentMapImpl(dataSource);
        persistentMap1.init("dic");
        persistentMap1.put("Red", "Красный");
        persistentMap1.put("Orange", "Оранжевый");
        persistentMap1.put("Green", "Зеленый");
        persistentMap1.put("Red", "Красный");
        persistentMap1.put("Blue", "Синий");
        persistentMap1.put("White", "Белый");
        persistentMap1.put("White", "Белый1");
        persistentMap1.put("White", "Белый2");
        persistentMap1.put("White", "Белый3");
        System.out.println(persistentMap1.getKeys().toString());
        System.out.println(persistentMap1.get("Red"));
        System.out.println(persistentMap1.get("White"));

        PersistentMap persistentMap2 = new PersistentMapImpl(dataSource);
        persistentMap2.init("null");
        System.out.println(persistentMap2.containsKey("null"));
        persistentMap2.put("null", null);
        System.out.println(persistentMap2.containsKey("null"));
        System.out.println(persistentMap2.get("null"));

    }

    public static DataSource initDb() throws SQLException {
        String createMapTable = "" + "drop table if exists persistent_map; " + "CREATE TABLE if not exists persistent_map (\n" + "   map_name varchar,\n" + "   KEY varchar,\n" + "   value varchar\n" + ");";
        DataSource dataSource = DbUtil.buildDataSource();
        DbUtil.applyDdl(createMapTable, dataSource);
        return dataSource;
    }
}
