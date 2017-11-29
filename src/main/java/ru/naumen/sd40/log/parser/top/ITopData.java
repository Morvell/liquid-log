package ru.naumen.sd40.log.parser.top;

import ru.naumen.sd40.log.parser.IData;

import static ru.naumen.sd40.log.parser.NumberUtils.getSafeDouble;
import static ru.naumen.sd40.log.parser.NumberUtils.roundToTwoPlaces;

public interface ITopData extends IData {
    void addLa(double la);
    void addCpu(double cpu);
    void addMem(double mem);
    public boolean isNan();

    public double getAvgLa();

    public double getAvgCpuUsage();

    public double getAvgMemUsage();

    public double getMaxLa();

    public double getMaxCpu();

    public double getMaxMem();
}
