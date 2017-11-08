package ru.naumen.sd40.log.parser;

import ru.naumen.perfhouse.interfaces.ITimeParser;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TopTimeParser implements ITimeParser {


    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyyMMddHH:mm",
            new Locale("ru", "RU"));

    private static final Pattern PATTERN = Pattern.compile("^_+ (\\S+)");

    private String dataDate;

    public TopTimeParser(String fileName, String timeZone){
        Matcher matcher = Pattern.compile("\\d{8}|\\d{4}-\\d{2}-\\d{2}").matcher(fileName);
        if (!matcher.find())
        {
            throw new IllegalArgumentException();
        }
        this.dataDate = matcher.group(0).replaceAll("-", "");
        DATE_FORMAT.setTimeZone(TimeZone.getTimeZone(timeZone));
    }

    @Override
    public long parseTime(String line) throws ParseException {
        Matcher matcher = PATTERN.matcher(line);
        if (matcher.find())
        {
            Date parse = DATE_FORMAT.parse(dataDate + matcher.group(1));
            return parse.getTime();
        }
        return 0L;
    }
}
