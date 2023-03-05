import java.util.Scanner;
import java.util.StringJoiner;

public class Stars {
    public static void main(String[] args) {
        stars();
        //stars2(); // just another implementation
        //stars3();
    }

    public static void stars() {
        try (Scanner scanner = new Scanner(System.in)) {
            int n = scanner.nextInt();
            int m = scanner.nextInt();
            char template = scanner.next().charAt(0);
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < m; j++) {
                    if (j < m - 1) {
                        System.out.print(template + " ");
                    } else {
                        System.out.print(template);
                    }
                }
                System.out.println();
            }
        }
    }

    // just another implementation
    public static void stars2() {
        try (Scanner scanner = new Scanner(System.in)) {
            int n = scanner.nextInt();
            int m = scanner.nextInt();
            String template = scanner.next();
            for (int i = 0; i < n; i++) {
                StringJoiner stringJoiner = new StringJoiner(" ");
                for (int j = 0; j < m; j++) {
                    stringJoiner.add(template);
                }
                System.out.println(stringJoiner);
            }
        }
    }

    //yet another implementation
    public static void stars3() {
        try (Scanner scanner = new Scanner(System.in)) {
            int n = scanner.nextInt();
            int m = scanner.nextInt();
            String template = scanner.next();
            for (int i = 0; i < n; i++) {

                StringBuilder stringBuilder = new StringBuilder();
                for (int j = 0; j < m; j++) {
                    stringBuilder.append(template).append(" ");
                }
                System.out.println(stringBuilder.toString().trim());
            }
        }
    }

}
