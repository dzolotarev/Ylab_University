package lesson2.statsaccum;

public class StatsAccumulatorTest {
    public static void main(String[] args) {
        StatsAccumulator sa = new StatsAccumulatorImpl();
        sa.add(1);
        sa.add(2);
        System.out.println(sa.getAvg());
        sa.add(0);
        System.out.println(sa.getMin());
        sa.add(3);
        sa.add(8);
        System.out.println(sa.getMax());
        System.out.println(sa.getCount());
    }
}
