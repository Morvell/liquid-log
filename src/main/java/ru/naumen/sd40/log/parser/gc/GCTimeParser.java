package ru.naumen.sd40.log.parser.gc;

import ru.naumen.perfhouse.interfaces.ITimeParser;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GCTimeParser implements ITimeParser {

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ",
            new Locale("ru", "RU"));

    private static final Pattern PATTERN = Pattern
            .compile("^(\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}.\\d{3}\\+\\d{4}).*");

    private boolean parse = false;

    public GCTimeParser()
    {
        DATE_FORMAT.setTimeZone(TimeZone.getTimeZone("GMT"));
    }

    public GCTimeParser(String timeZone)
    {
        DATE_FORMAT.setTimeZone(TimeZone.getTimeZone(timeZone));
    }

    @Override
    public long parseTime(String line) throws ParseException
    {
        Matcher matcher = PATTERN.matcher(line);
        long time = 0L;
        if (matcher.find())
        {
            Date parserDate = DATE_FORMAT.parse(matcher.group(1));
            time = parserDate.getTime();
            parse = time != 0;
        }
        return time;
    }

    @Override
    public boolean isParse() {
        return parse;
    }
}
