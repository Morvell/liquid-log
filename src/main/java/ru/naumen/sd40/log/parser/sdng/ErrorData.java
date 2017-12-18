package ru.naumen.sd40.log.parser.sdng;

import ru.naumen.perfhouse.interfaces.IDataParser;
import ru.naumen.sd40.log.parser.IData;

import java.util.regex.Pattern;

/**
 * Created by doki on 22.10.16.
 */
public class ErrorData implements IData
{
    private long warnCount;
    private long errorCount;
    private long fatalCount;

    public void incWarnCount() {
        this.warnCount++;
    }

    public void incErrorCount() {
        this.errorCount++;
    }

    public void incFatalCount() {
        this.fatalCount++;
    }

    public long getWarnCount()
    {
        return warnCount;
    }

    public long getErrorCount()
    {
        return errorCount;
    }

    public long getFatalCount()
    {
        return fatalCount;
    }
}
