package ru.naumen.sd40.log.parser;

import ru.naumen.perfhouse.interfaces.IDataParser;

public class GCDataParser implements IDataParser {

    private GCParser gc;

    public GCDataParser() {
        gc = new GCParser();
    }

    @Override
    public void parseLine(String line) {
        gc.parseLine(line);
    }

    public GCParser getGc() {
        return gc;
    }
}
