package io.ylab.intensive.lesson01;

import java.util.Scanner;

public class Pell {
    public static void main(String[] args) {
        //pell();
        pell1();
    }

    public static void pell() {
        try (Scanner scanner = new Scanner(System.in)) {
            int n = scanner.nextInt();

            int[] P = new int[n + 1];

            P[1] = 1;
            for (int i = 2; i <= n; i++) {
                P[i] = 2 * P[i - 1] + P[i - 2];
            }
            System.out.println(P[n]);
        }
    }

    public static void pell1() {
        try (Scanner scanner = new Scanner(System.in)) {
            int n = scanner.nextInt();

            int P = 0;
            int Pn1 = 1;
            int Pn2 = 0;
            for (int i = 2; i <= n; i++) {
                P = 2 * Pn1 + Pn2;
                Pn2 = Pn1;
                Pn1 = P;

            }
            System.out.println(P);
        }

    }
}
