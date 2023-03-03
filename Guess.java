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

                if (n == number) {
                    attempts++;
                    System.out.println("Ты угадал с " + attempts + " попытки");
                    break;
                } else if (n > number) {
                    System.out.print("Мое число меньше!");
                    attempts++;
                } else{
                    System.out.print("Мое число больше!");
                    attempts++;
                }
                System.out.println(" Осталось " + --maxAttempts + " попыток");


                if (maxAttempts == 0) {
                    System.out.println("Ты не угадал");
                }
            }
            while (maxAttempts > 0);
        }
    }
}
