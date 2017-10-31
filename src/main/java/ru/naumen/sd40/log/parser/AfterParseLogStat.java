package ru.naumen.sd40.log.parser;

public class AfterParseLogStat {
    private long timestemp;
    private long error;
    private double min;
    private double mean;
    private double stddev;
    private double percent50;
    private double percent95;
    private double percent99;
    private double percent999;
    private double max;
    private long count;

    public AfterParseLogStat(ActionDoneParser done, long timestemp, long error){
        setError(error);
        setTimestemp(timestemp);
        setMin(done.getMin());
        setMean(done.getMean());
        setStddev(done.getStddev());
        setPercent50(done.getPercent50());
        setPercent95(done.getPercent95());
        setPercent99(done.getPercent99());
        setPercent999(done.getPercent999());
        setMax(done.getMax());
        setCount(done.getCount());

    }

    public long getTimestemp() {
        return timestemp;
    }

    private void setTimestemp(long timestemp) {
        this.timestemp = timestemp;
    }

    public long getError() {
        return error;
    }

    private void setError(long error) {
        this.error = error;
    }

    public double getMin() {
        return min;
    }

    public void setMin(double min) {
        this.min = min;
    }

    public double getMean() {
        return mean;
    }

    private void setMean(double mean) {
        this.mean = mean;
    }

    public double getStddev() {
        return stddev;
    }

    private void setStddev(double stddev) {
        this.stddev = stddev;
    }

    public double getPercent50() {
        return percent50;
    }

    private void setPercent50(double percent50) {
        this.percent50 = percent50;
    }

    public double getPercent95() {
        return percent95;
    }

    private void setPercent95(double percent95) {
        this.percent95 = percent95;
    }

    public double getPercent99() {
        return percent99;
    }

    private void setPercent99(double percent99) {
        this.percent99 = percent99;
    }

    public double getPercent999() {
        return percent999;
    }

    private void setPercent999(double percent999) {
        this.percent999 = percent999;
    }

    public double getMax() {
        return max;
    }

    private void setMax(double max) {
        this.max = max;
    }

    public long getCount() {
        return count;
    }

    private void setCount(long count) {
        this.count = count;
    }
}
