package lesson2.ratelimitedprinter;

public class RateLimitedPrinter {
    private int interval;
    private long time;

    public RateLimitedPrinter(int interval) {
        this.interval = interval;
    }

    public void print(String message) {
        if (System.currentTimeMillis() - this.time > this.interval) {
            System.out.println(message);
            this.time = System.currentTimeMillis();
        }
    }
}
