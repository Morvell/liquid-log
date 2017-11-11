package ru.naumen.perfhouse.interfaces;

import java.text.ParseException;

public interface ITimeParser {

    long parseTime(String line) throws ParseException;
}
