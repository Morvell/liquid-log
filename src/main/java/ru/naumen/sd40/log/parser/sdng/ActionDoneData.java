package ru.naumen.sd40.log.parser.sdng;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
import ru.naumen.sd40.log.parser.IData;

/**
 * Created by doki on 22.10.16.
 */
public class ActionDoneData implements IData
{

    private ArrayList<Integer> times = new ArrayList<>();
    private double min;
    private double mean;
    private double stddev;
    private double percent50;
    private double percent95;
    private double percent99;

    private double percent999;
    private double max;
    private long count;
    private int addObjectActions = 0;
    private int editObjectsActions = 0;
    private int getCatalogsActions = 0;
    private int getListActions = 0;
    private int commentActions = 0;

    private int getFormActions = 0;

    private int getDtObjectActions = 0;

    private int searchActions = 0;

    private boolean nan = true;

    private HashMap<String, Integer> actions = new HashMap<>();


    public void incAddObjectActions() {
        this.addObjectActions++;
    }

    public void incEditObjectsActions() {
        this.editObjectsActions++;
    }

    public void incGetCatalogsActions() {
        this.getCatalogsActions++;
    }

    public void incGetListActions() {
        this.getListActions++;
    }

    public void incCommentActions() {
        this.commentActions++;
    }

    public void incGetFormActions() {
        this.getFormActions++;
    }

    public void incGetDtObjectActions() {
        this.getDtObjectActions++;
    }

    public void incSearchActions() {
        this.searchActions++;
    }

    public void calculate()
    {
        DescriptiveStatistics ds = new DescriptiveStatistics();
        times.forEach(t -> ds.addValue(t));
        min = ds.getMin();
        mean = ds.getMean();
        stddev = ds.getStandardDeviation();
        percent50 = ds.getPercentile(50.0);
        percent95 = ds.getPercentile(95.0);
        percent99 = ds.getPercentile(99.0);
        percent999 = ds.getPercentile(99.9);
        max = ds.getMax();
        count = ds.getN();
        nan = count == 0;
    }

    public int geListActions()
    {
        return getListActions;
    }

    public HashMap<String, Integer> getActionsCounter()
    {
        return actions;
    }

    public int getAddObjectActions()
    {
        return addObjectActions;
    }

    public int getGetCatalogsActions() { return getCatalogsActions;}

    public int getCommentActions()
    {
        return commentActions;
    }

    public long getCount()
    {
        return count;
    }

    public int getDtObjectActions()
    {
        return getDtObjectActions;
    }

    public int getEditObjectsActions()
    {
        return editObjectsActions;
    }

    public int getFormActions()
    {
        return getFormActions;
    }

    public double getMax()
    {
        return max;
    }

    public double getMean()
    {
        return mean;
    }

    public double getMin()
    {
        return min;
    }

    public double getPercent50()
    {
        return percent50;
    }

    public double getPercent95()
    {
        return percent95;
    }

    public double getPercent99()
    {
        return percent99;
    }

    public double getPercent999()
    {
        return percent999;
    }

    public int getSearchActions()
    {
        return searchActions;
    }

    public double getStddev()
    {
        return stddev;
    }

    public ArrayList<Integer> getTimes()
    {
        return times;
    }

    public boolean isNan()
    {
        return nan;
    }

    public void setTimes(int times) {
        this.times.add(times);
    }

}
