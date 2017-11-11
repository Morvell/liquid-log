package ru.naumen.sd40.log.parser;

import ru.naumen.perfhouse.interfaces.IDataParser;

import java.io.IOException;
import java.text.ParseException;

public class TopDataParser implements IDataParser{

    TopParser topParser;

    public TopDataParser() {
        topParser = new TopParser();
    }


    public void parseLine(String line) throws IOException, ParseException {
        topParser.parseLine(line);
    }

    public TopData getCpu() {
        return topParser.getCpuData();
    }
}
