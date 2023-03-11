package lesson2.statsaccum;

public class StatsAccumulatorImpl implements StatsAccumulator {
    private int previousValue;
    private int sum;
    private int min;
    private int max;
    private int counter;
    private Double avg;


    @Override
    public void add(int value) {
        counter++;
        min = Math.min(previousValue, value);
        max = Math.max(previousValue, value);
        sum += value;
        if (counter > 0) {
            avg = (double) sum / counter;
        }
        previousValue = value;
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
        return avg;
    }
}
