package ru.naumen.sd40.log.parser.top;

import ru.naumen.perfhouse.interfaces.ITimeParser;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TopTimeParser implements ITimeParser {

    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHH:mm");

    private final Pattern timeRegex = Pattern.compile("^_+ (\\S+)");

    private long currentTime = 0;

    private boolean parse = false;

    private String fileName;

    public TopTimeParser(String fileName) {

        Matcher matcher = Pattern.compile("\\d{8}|\\d{4}-\\d{2}-\\d{2}").matcher(fileName);
        if (!matcher.find())
        {
            throw new IllegalArgumentException();
        }
        this.fileName = matcher.group(0).replaceAll("-", "");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
    }

    public TopTimeParser(String fileName, String timeZone) {
        Matcher matcher = Pattern.compile("\\d{8}|\\d{4}-\\d{2}-\\d{2}").matcher(fileName);
        if (!matcher.find())
        {
            throw new IllegalArgumentException();
        }
        this.fileName = matcher.group(0).replaceAll("-", "");
        sdf.setTimeZone(TimeZone.getTimeZone(timeZone));
    }

    @Override
    public long parseTime(String line) throws ParseException {
        long time = 0;
        Matcher matcher = timeRegex.matcher(line);
        if (matcher.find())
        {

            time = sdf.parse(this.fileName + matcher.group(1)).getTime();
        }

        if (time == currentTime || (currentTime !=0 && time ==0)){
            parse = true;
        }
        else parse = false;

        currentTime = time;

        return time;
    }

    @Override
    public boolean isParse() {
        return parse;
    }
}
