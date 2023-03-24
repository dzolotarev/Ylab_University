package io.ylab.intensive.lesson03.orgstructure;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Denis Zolotarev
 */
public class OrgStructureParserImpl implements OrgStructureParser {

    @Override
    public Employee parseStructure(File csvFile) {
        Map<Long, Employee> preparedMap = prepareEmployeeMap(csvFile);
        return adjustEmployeeMapAndGetBoss(preparedMap);
    }

    private Map<Long, Employee> prepareEmployeeMap(File csvFile) {
        Map<Long, Employee> employeeMap = new HashMap<>();

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(csvFile))) {
            bufferedReader.readLine(); //пропускаем первую строку
            while (bufferedReader.ready()) {
                String line = bufferedReader.readLine();
                if (!line.isEmpty()) {
                    String[] fields = line.trim().split(";");
                    Long id = Long.parseLong(fields[0]);
                    Long boss_id = null;
                    if (!fields[1].isEmpty()) {
                        boss_id = Long.parseLong(fields[1]);
                    }
                    String name = fields[2];
                    String position = fields[3];
                    Employee employee = new Employee();
                    employee.setId(id);
                    employee.setBossId(boss_id);
                    employee.setName(name);
                    employee.setPosition(position);
                    employeeMap.put(id, employee);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return employeeMap;
    }

    private Employee adjustEmployeeMapAndGetBoss(Map<Long, Employee> prepareEmployeeMap) {
        Employee boss = null;
        for (Map.Entry<Long, Employee> entry : prepareEmployeeMap.entrySet()) {
            Long employeeId = entry.getKey();
            Employee employee = entry.getValue();
            Long boss_id = employee.getBossId();
            Employee employeeBoss = prepareEmployeeMap.get(boss_id);
            employee.setBoss(boss);
            if (boss_id != null) {
                employeeBoss.getSubordinate().add(employee);
                prepareEmployeeMap.put(employeeBoss.getId(), employeeBoss);
            } else {
                boss = employee;
            }
            prepareEmployeeMap.put(employeeId, employee);
        }
        return boss;
    }
}
