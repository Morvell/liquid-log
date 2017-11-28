package ru.naumen.sd40.log.parser.sdng;

import org.springframework.stereotype.Component;
import ru.naumen.perfhouse.interfaces.IDataParser;
import ru.naumen.sd40.log.parser.ActionDoneParser;
import ru.naumen.sd40.log.parser.ErrorParser;
import ru.naumen.sd40.log.parser.IData;

@Component
public class SdngDataParser implements IDataParser {

    @Override
    public void parseLine(IData data, String line) {

        ISdngData sdngData = (ISdngData) data;
        sdngData.getErrors().parseLine(line);
        sdngData.getActionsDone().parseLine(line);
    }

}
