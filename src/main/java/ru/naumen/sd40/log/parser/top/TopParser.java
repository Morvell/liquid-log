package ru.naumen.sd40.log.parser.top;

import org.springframework.stereotype.Component;
import ru.naumen.perfhouse.interfaces.IDataParser;
import ru.naumen.sd40.log.parser.IData;

import java.io.IOException;
import java.text.ParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Top output parser
 * @author dkolmogortsev
 *
 */

@Component
public class TopParser implements IDataParser
{

    private final Pattern cpuAndMemPattren = Pattern
            .compile("^ *\\d+ \\S+ +\\S+ +\\S+ +\\S+ +\\S+ +\\S+ +\\S+ \\S+ +(\\S+) +(\\S+) +\\S+ java");


    public void parseLine(IData data, String line)
    {
        ITopData topData = (ITopData) data;
        //get la
            Matcher la = Pattern.compile(".*load average:(.*)").matcher(line);
            if (la.find())
            {
                topData.addLa(Double.parseDouble(la.group(1).split(",")[0].trim()));
                return;
            }

            //get cpu and mem
            Matcher cpuAndMemMatcher = cpuAndMemPattren.matcher(line);
            if (cpuAndMemMatcher.find())
            {
                topData.addCpu(Double.valueOf(cpuAndMemMatcher.group(1)));
                topData.addMem(Double.valueOf(cpuAndMemMatcher.group(2)));

            }

    }

}
