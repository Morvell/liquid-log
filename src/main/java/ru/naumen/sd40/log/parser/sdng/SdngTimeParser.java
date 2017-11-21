package ru.naumen.sd40.log.parser.sdng;

import ru.naumen.perfhouse.interfaces.ITimeParser;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by doki on 22.10.16.
 */
public class SdngTimeParser implements ITimeParser
{
    private static final Pattern TIME_PATTERN = Pattern
            .compile("^\\d+ \\[.*?\\] \\((\\d{2} .{3} \\d{4} \\d{2}:\\d{2}:\\d{2},\\d{3})\\)");
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd MMM yyyy HH:mm:ss,SSS",
            new Locale("ru", "RU"));

    private boolean parse = false;

    public SdngTimeParser()
    {
        DATE_FORMAT.setTimeZone(TimeZone.getTimeZone("GMT"));
    }

    public SdngTimeParser(String zoneId)
    {
        DATE_FORMAT.setTimeZone(TimeZone.getTimeZone(zoneId));
    }

    @Override
    public long parseTime(String line) throws ParseException
    {
        Matcher matcher = TIME_PATTERN.matcher(line);
        if (matcher.find())
        {
            String timeString = matcher.group(1);
            Date recDate = DATE_FORMAT.parse(timeString);
            return recDate.getTime();
        }
        return 0L;
    }

    @Override
    public boolean isParse() {
        return parse;
    }
}
