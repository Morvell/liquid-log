package ru.naumen.perfhouse.interfaces;

import java.io.IOException;
import java.text.ParseException;

public interface IDataParser {

    void parseLine(String line) throws IOException, ParseException;
}
