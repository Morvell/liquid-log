package ru.naumen.perfhouse.interfaces;

import java.text.ParseException;
import java.util.regex.Pattern;

public interface ITimeParser {

    long parseTime(String line) throws ParseException;
}
