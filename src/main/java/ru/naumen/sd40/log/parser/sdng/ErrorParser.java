package ru.naumen.sd40.log.parser.sdng;

import ru.naumen.perfhouse.interfaces.IDataParser;
import ru.naumen.sd40.log.parser.IData;

import java.util.regex.Pattern;

public class ErrorParser implements IDataParser {

    private final Pattern warnRegEx = Pattern.compile("^\\d+ \\[.+?\\] \\(.+?\\) WARN");
    private final Pattern errorRegEx = Pattern.compile("^\\d+ \\[.+?\\] \\(.+?\\) ERROR");
    private final Pattern fatalRegEx = Pattern.compile("^\\d+ \\[.+?\\] \\(.+?\\) FATAL");

    public void parseLine(IData data, String line)
    {
        ErrorData errorData = (ErrorData) data;
        if (warnRegEx.matcher(line).find())
        {
            errorData.incWarnCount();
        }
        if (errorRegEx.matcher(line).find())
        {
            errorData.incErrorCount();
        }
        if (fatalRegEx.matcher(line).find())
        {
            errorData.incFatalCount();
        }
    }
}
