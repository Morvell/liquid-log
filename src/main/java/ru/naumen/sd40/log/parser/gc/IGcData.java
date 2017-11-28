package ru.naumen.sd40.log.parser.gc;

import ru.naumen.sd40.log.parser.IData;

public interface IGcData extends IData {

    double getCalculatedAvg();
    long getGcTimes();
    double getMaxGcTime();
    boolean isNan();
    void addValue(Double data);
}
