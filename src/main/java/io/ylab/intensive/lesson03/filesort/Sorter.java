package io.ylab.intensive.lesson03.filesort;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.TreeMap;

/**
 * @author Denis Zolotarev
 */
public class Sorter {

    private int maxLinesCount = 10_000_000; //максимальное кол-во строк для чтения в память/ сортировки / записи в кусок
    private String tmpDir = ""; //временная директория для хранения кусков

    public void setMaxLinesCount(int count) {
        maxLinesCount = count;
    }

    public void setTmpDir(String name) {
        tmpDir = name;
    }

    public File sortFile(File file) throws IOException {
        List<File> files = splitFile(file);
        File sortedFile = createSortedFile(file);
        mergeFiles(files, sortedFile);
        return sortedFile;
    }

    private File createSortedFile(File file) {
        String fileName = file.getName().substring(0, file.getName().length() - 4);
        return new File(file.getParent() + "/" + fileName + "_sorted_" + System.currentTimeMillis() + ".txt");
    }

    private List<File> splitFile(File file) throws IOException {
        List<File> result = new ArrayList<>();
        List<Long> numbers = new ArrayList<>();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            String line;
            int linesCount = 0;
            while ((line = bufferedReader.readLine()) != null) {
                numbers.add(Long.parseLong(line));
                linesCount++;
                if (linesCount >= maxLinesCount) {
                    linesCount = 0;
                    result.add(sortAndWriteToFile(numbers));
                    numbers.clear();
                }
            }

            if (linesCount > 0) {
                result.add(sortAndWriteToFile(numbers));
            }
        }
        return result;
    }

    private void writeNumbersToFile(List<Long> numbers, File outFile) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outFile))) {
            for (Long number : numbers) {
                writer.write(number.toString() + "\n");
            }
            writer.flush();
        }
    }

    private File sortAndWriteToFile(List<Long> numbers) throws IOException {
        Collections.sort(numbers);
        File file = createTempFile();
        writeNumbersToFile(numbers, file);
        return file;
    }

    private File createTempFile() {
        return new File(tmpDir + "temp_" + System.currentTimeMillis() + ".txt");
    }

    private void mergeFiles(List<File> files, File outFile) throws IOException {
        TreeMap<Long, BufferedReader> map = new TreeMap<>();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outFile))) {
            for (File file : files) {
                BufferedReader reader = new BufferedReader(new FileReader(file));
                String line = reader.readLine();
                map.put(Long.parseLong(line), reader);
            }

            while (map.size() > 0) {
                Long number = map.firstKey();
                writer.write(number.toString() + "\n");
                BufferedReader reader = map.remove(number);
                String nextLine = reader.readLine();
                if (nextLine != null) {
                    Long num = Long.parseLong(nextLine);
                    map.put(num, reader);
                } else {
                    closeSilently(reader);
                }
            }
        } finally {
            if (map.size() > 0) {
                for (BufferedReader reader : map.values()) {
                    closeSilently(reader);
                }
            }
            for (File file : files) {
                file.delete();
            }
        }
    }

    private void closeSilently(BufferedReader reader) {
        try {
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}