package io.ylab.intensive.lesson03.filesort;

import java.io.File;
import java.io.IOException;

/**
 * @author Denis Zolotarev
 */
public class Test {
    public static void main(String[] args) throws IOException {
        //File dataFile = new Generator().generate("/Users/denis/Downloads/data.txt", 375_000_000);
        File dataFile = new Generator().generate("/Users/denis/Downloads/data.txt", 100_000_000);


        System.out.println("Sorted? " + new Validator(dataFile).isSorted()); // false
        long startTime = System.currentTimeMillis();
        Sorter sorter = new Sorter();
        sorter.setMaxLinesCount(10_000_000);  //можно настроить кол-во строк для чтения за раз в память. Чем меньше строк мы читаем, тем больше будет создано временных фалов
        sorter.setTmpDir("/tmp/"); //устанавливаем директория для временных файлов.
        File sortedFile = sorter.sortFile(dataFile); //результат будет записан в директорию, рядом с исходным файлом

        System.out.printf("Time spent: %d sec%n", (System.currentTimeMillis() - startTime) / 1000);
        System.out.println("Sorted? " + new Validator(sortedFile).isSorted()); // true

    }
}
