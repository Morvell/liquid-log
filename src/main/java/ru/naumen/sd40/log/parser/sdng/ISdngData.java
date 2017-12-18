package ru.naumen.sd40.log.parser.sdng;

import ru.naumen.sd40.log.parser.IData;

public interface ISdngData extends IData {

    ActionDoneData getActionsDone();

    ErrorData getErrors();
}
