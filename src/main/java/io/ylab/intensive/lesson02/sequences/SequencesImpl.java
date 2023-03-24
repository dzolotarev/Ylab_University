package io.ylab.intensive.lesson02.sequences;

public class SequencesImpl implements Sequences {

    private boolean isValid(int n) {
        if (n <= 0) {
            System.out.println("Ошибка! Введите число больше нуля!");
            return false;
        }
        return true;
    }

    private boolean isEven(int n) {
        return n % 2 == 0;
    }

    @Override
    public void a(int n) {
        if (isValid(n)) {
            for (int i = 1; i <= n; i++) {
                System.out.println(i * 2);
            }
        }
    }

    @Override
    public void b(int n) {
        if (isValid(n)) {
            for (int i = 1; i <= n; i++) {
                System.out.println(i * 2 - 1);
            }
        }
    }

    @Override
    public void c(int n) {
        if (isValid(n)) {
            for (int i = 1; i <= n; i++) {
                System.out.println(i * i);
            }
        }
    }

    @Override
    public void d(int n) {
        if (isValid(n)) {
            for (int i = 1; i <= n; i++) {
                System.out.println(i * i * i);
            }
        }
    }

    @Override
    public void e(int n) {
        if (isValid(n)) {
            for (int i = 1; i <= n; i++) {
                if (isEven(i)) {
                    System.out.println(-1);
                } else {
                    System.out.println(1);
                }
            }
        }
    }

    @Override
    public void f(int n) {
        if (isValid(n)) {
            for (int i = 1; i <= n; i++) {
                if (isEven(i)) {
                    System.out.println(-i);
                } else {
                    System.out.println(i);
                }
            }
        }
    }

    @Override
    public void g(int n) {
        if (isValid(n)) {
            for (int i = 1; i <= n; i++) {
                if (isEven(i)) {
                    System.out.println(-i * i);
                } else {
                    System.out.println(i * i);
                }
            }
        }
    }

    @Override
    public void h(int n) {
        if (isValid(n)) {
            int result = 1;
            for (int i = 1; i <= n; i++) {
                if (isEven(i)) {
                    System.out.println(0);
                } else {
                    System.out.println(result);
                    result++;
                }
            }
        }
    }

    @Override
    public void i(int n) {
        if (isValid(n)) {
            int result = 1;
            for (int i = 1; i <= n; i++) {
                result *= i;
                System.out.println(result);
            }
        }
    }

    @Override
    public void j(int n) {
        if (isValid(n)) {
            int i1 = 1;
            int i2 = 1;
            int result;
            for (int i = 1; i <= n; i++) {
                if (i == 1 || i == 2) {
                    System.out.println(1);
                } else {
                    result = i1 + i2;
                    System.out.println(result);
                    i2 = i1;
                    i1 = result;
                }
            }
        }
    }
}
