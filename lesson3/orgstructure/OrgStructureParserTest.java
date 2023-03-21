package lesson3.orgstructure;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

/**
 * @author Denis Zolotarev
 */
public class OrgStructureParserTest {
    public static void main(String[] args) throws IOException {
        System.out.println("Файл CSV (полный путь):");
        Scanner scanner = new Scanner(System.in);
        String filePath = scanner.nextLine();
        scanner.close();

        //File csvFile = new File("company.csv");
        File csvFile = new File(filePath);

        OrgStructureParser orgStructureParser = new OrgStructureParserImpl();
        Employee boss = orgStructureParser.parseStructure(csvFile);

        System.out.println("Босс:");
        String bossString = String.format("ID: %s, Имя: %s, Должность: %s", boss.getId(), boss.getName(), boss.getPosition());
        System.out.println(bossString);

        System.out.println("Список подчиненных:");
        for (Employee employee : boss.getSubordinate()) {
            System.out.printf("ID: %s, Имя: %s, Позиция: %s%n", employee.getId(), employee.getName(), employee.getPosition());
        }
    }
}
