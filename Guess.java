import java.util.Random;
import java.util.Scanner;

public class Guess {
    public static void main(String[] args) {
        int number = new Random().nextInt(99) + 1; // здесь загадывается число от 1 до 99
        int maxAttempts = 10; // здесь задается количество попыток
        int attempts = 0;
        System.out.println("Я загадал число от 1 до 100. У тебя " + maxAttempts + " попыток угадать.");
        try (Scanner scanner = new Scanner(System.in)) {
            do {
                int n = scanner.nextInt();

                if (n > number) {
                    System.out.println("Мое число меньше! Осталось " + --maxAttempts + " попыток");
                    attempts++;
                } else if (n < number) {
                    System.out.println("Мое число больше! Осталось " + --maxAttempts + " попыток");
                    attempts++;
                } else {
                    attempts++;
                    System.out.println("Ты угадал с " + attempts + " попытки");
                    break;
                }
                if (maxAttempts == 0) {
                    System.out.println("Ты не угадал");
                }

            }
            while (maxAttempts > 0);
        }
    }
}
