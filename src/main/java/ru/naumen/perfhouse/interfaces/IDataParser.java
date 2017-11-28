package ru.naumen.perfhouse.interfaces;

import ru.naumen.sd40.log.parser.IData;

import java.io.IOException;
import java.text.ParseException;

public interface IDataParser extends IData {

    void parseLine(IData data, String line);
}
