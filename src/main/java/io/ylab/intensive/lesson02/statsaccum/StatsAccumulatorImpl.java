package io.ylab.intensive.lesson02.statsaccum;

public class StatsAccumulatorImpl implements StatsAccumulator {
    private int sum;
    private int counter;
    private int min = Integer.MAX_VALUE;
    private int max = Integer.MIN_VALUE;

    @Override
    public void add(int value) {
        counter++;
        sum += value;
        min = Math.min(min, value);
        max = Math.max(max, value);
    }

    @Override
    public int getMin() {
        return min;
    }

    @Override
    public int getMax() {
        return max;
    }

    @Override
    public int getCount() {
        return counter;
    }

    @Override
    public Double getAvg() {
        if (counter > 0) {
            return (double) sum / counter;
        }
        return 0.0;
    }
}
