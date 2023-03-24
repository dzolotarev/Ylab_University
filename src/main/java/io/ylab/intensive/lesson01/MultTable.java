package io.ylab.intensive.lesson01;

public class MultTable {

    public static void main(String[] args) {
        multTable();
        System.out.println();
        multTable2();
    }

    public static void multTable() {
        for (int i = 1; i < 10; i++) {
            for (int j = 1; j < 10; j++) {
                System.out.printf("%d x %d = %d\n", i, j, i * j);
            }
        }
    }

    public static void multTable2() {
        int[][] multiTable = new int[10][10];
        for (int i = 1; i < 10; i++) {
            for (int j = 1; j < 10; j++) {
                multiTable[i][j] = (i) * (j);
            }
        }
        for (int i = 1; i < 10; i++) {
            for (int j = 1; j < 10; j++) {
                if (j < 9) {
                    System.out.print(multiTable[i][j] + " ");
                } else {
                    System.out.print(multiTable[i][j] + "\n");
                }
            }
        }
    }
}
