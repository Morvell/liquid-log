package ru.naumen.sd40.log.parser.sdng;

public class SdngData implements ISdngData {

    private ActionDoneData actionsDone;
    private ErrorData errors;


    public SdngData() {
        actionsDone = new ActionDoneData();
        errors = new ErrorData();
    }

    public ActionDoneData getActionsDone() {
        return actionsDone;
    }

    public ErrorData getErrors() {
        return errors;
    }
}
