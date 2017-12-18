package ru.naumen.sd40.log.parser.sdng;

import org.springframework.stereotype.Component;
import ru.naumen.perfhouse.interfaces.IDataParser;
import ru.naumen.sd40.log.parser.IData;

@Component
public class SdngDataParser implements IDataParser {

    private final ActionDoneParser actionDoneParser;
    private final ErrorParser errorParser;

    public SdngDataParser() {
        actionDoneParser = new ActionDoneParser();
        errorParser = new ErrorParser();
    }

    @Override
    public void parseLine(IData data, String line) {

        ISdngData sdngData = (ISdngData) data;
        actionDoneParser.parseLine(sdngData.getActionsDone(),line);
        errorParser.parseLine(sdngData.getErrors(),line);
    }

}
