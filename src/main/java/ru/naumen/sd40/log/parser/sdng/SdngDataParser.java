package ru.naumen.sd40.log.parser.sdng;

import ru.naumen.perfhouse.interfaces.IDataParser;
import ru.naumen.sd40.log.parser.ActionDoneParser;
import ru.naumen.sd40.log.parser.ErrorParser;

public class SdngDataParser implements IDataParser {

    private ActionDoneParser actionsDone;
    private ErrorParser errors;

    public SdngDataParser() {
        actionsDone = new ActionDoneParser();
        errors = new ErrorParser();
    }

    @Override
    public void parseLine(String line) {
        errors.parseLine(line);
        actionsDone.parseLine(line);
    }

    public ActionDoneParser getActionsDone() {
        return actionsDone;
    }

    public ErrorParser getErrors() {
        return errors;
    }
}
