package ru.naumen.sd40.log.parser.sdng;

import ru.naumen.sd40.log.parser.ActionDoneParser;
import ru.naumen.sd40.log.parser.ErrorParser;

public class SdngData {

    private ActionDoneParser actionsDone;
    private ErrorParser errors;


    public SdngData() {
        actionsDone = new ActionDoneParser();
        errors = new ErrorParser();
    }

    public ActionDoneParser getActionsDone() {
        return actionsDone;
    }

    public ErrorParser getErrors() {
        return errors;
    }
}
