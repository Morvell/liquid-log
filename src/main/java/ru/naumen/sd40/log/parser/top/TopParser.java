package ru.naumen.sd40.log.parser.top;

import ru.naumen.perfhouse.interfaces.IDataParser;
import ru.naumen.sd40.log.parser.TopData;

import java.io.*;
import java.text.ParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Top output parser
 * @author dkolmogortsev
 *
 */
public class TopParser implements IDataParser
{

    private final Pattern cpuAndMemPattren = Pattern
            .compile("^ *\\d+ \\S+ +\\S+ +\\S+ +\\S+ +\\S+ +\\S+ +\\S+ \\S+ +(\\S+) +(\\S+) +\\S+ java");

    private TopData cpuData = new TopData();

    public void parseLine(String line)
    {
        //get la
            Matcher la = Pattern.compile(".*load average:(.*)").matcher(line);
            if (la.find())
            {
                cpuData.addLa(Double.parseDouble(la.group(1).split(",")[0].trim()));
                return;
            }

            //get cpu and mem
            Matcher cpuAndMemMatcher = cpuAndMemPattren.matcher(line);
            if (cpuAndMemMatcher.find())
            {
                cpuData.addCpu(Double.valueOf(cpuAndMemMatcher.group(1)));
                cpuData.addMem(Double.valueOf(cpuAndMemMatcher.group(2)));

            }

    }

    public TopData getCpuData() {
        return cpuData;
    }
}
