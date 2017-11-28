package ru.naumen.sd40.log.parser.top;

import ru.naumen.sd40.log.parser.IData;

public interface ITopData extends IData {
    void addLa(double la);
    void addCpu(double cpu);
    void addMem(double mem);
}
