package ru.naumen.sd40.log.parser;

import java.io.*;
import java.text.ParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Top output parser
 * @author dkolmogortsev
 *
 */
public class TopParser
{

    private Pattern cpuAndMemPattren = Pattern
            .compile("^ *\\d+ \\S+ +\\S+ +\\S+ +\\S+ +\\S+ +\\S+ +\\S+ \\S+ +(\\S+) +(\\S+) +\\S+ java");

    private TopData cpuData = new TopData();

    public void parseLine(String line) throws IOException, ParseException
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
