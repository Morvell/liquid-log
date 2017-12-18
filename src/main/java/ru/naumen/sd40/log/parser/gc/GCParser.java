package ru.naumen.sd40.log.parser.gc;

import static ru.naumen.sd40.log.parser.NumberUtils.getSafeDouble;
import static ru.naumen.sd40.log.parser.NumberUtils.roundToTwoPlaces;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
import org.springframework.stereotype.Component;
import ru.naumen.perfhouse.interfaces.IDataParser;
import ru.naumen.sd40.log.parser.IData;


@Component
public class GCParser implements IDataParser
{
    private Pattern gcExecutionTime = Pattern.compile(".*real=(.*)secs.*");

    public void parseLine(IData data,String line)
    {
        Matcher matcher = gcExecutionTime.matcher(line);



        if (matcher.find())
        {
            IGcData ds = (IGcData) data;
            ds.addValue(Double.parseDouble(matcher.group(1).trim().replace(',', '.')));
        }
    }
}
