package lesson2.sequences;

public class SequencesImpl implements Sequences {
    @Override
    public void a(int n) {
        checkN(n);
        for (int i = 1; i <= n; i++) {
            System.out.println(i * 2);
        }
    }

    @Override
    public void b(int n) {
        checkN(n);
        for (int i = 1; i <= n; i++) {
            System.out.println(i * 2 - 1);
        }
    }

    @Override
    public void c(int n) {
        checkN(n);
        for (int i = 1; i <= n; i++) {
            System.out.println(i * i);
        }
    }

    @Override
    public void d(int n) {
        checkN(n);
        for (int i = 1; i <= n; i++) {
            System.out.println(i * i * i);
        }
    }

    @Override
    public void e(int n) {
        checkN(n);
        for (int i = 1; i <= n; i++) {
            if (i % 2 == 0) {
                System.out.println(-1);
            } else {
                System.out.println(1);
            }
        }
    }

    @Override
    public void f(int n) {
        checkN(n);
        for (int i = 1; i <= n; i++) {
            if (i % 2 == 0) {
                System.out.println(-i);
            } else {
                System.out.println(i);
            }
        }
    }

    @Override
    public void g(int n) {
        checkN(n);
        for (int i = 1; i <= n; i++) {
            if (i % 2 == 0) {
                System.out.println(-i * i);
            } else {
                System.out.println(i * i);
            }
        }
    }

    @Override
    public void h(int n) {
        checkN(n);
        int result = 1;
        for (int i = 1; i <= n; i++) {
            if (i % 2 == 0) {
                System.out.println(0);
            } else {
                System.out.println(result);
                result++;
            }
        }
    }

    @Override
    public void i(int n) {
        checkN(n);
        int result = 1;
        for (int i = 1; i <= n; i++) {
            result *= i;
            System.out.println(result);
        }
    }

    @Override
    public void j(int n) {
        checkN(n);
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

    private void checkN(int n) {
        if (n <= 0) {
            System.out.println("Введите число больше нуля");
        }
    }
}
