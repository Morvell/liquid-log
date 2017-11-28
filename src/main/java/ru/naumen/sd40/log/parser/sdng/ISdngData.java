package ru.naumen.sd40.log.parser.sdng;

import ru.naumen.sd40.log.parser.ActionDoneParser;
import ru.naumen.sd40.log.parser.ErrorParser;
import ru.naumen.sd40.log.parser.IData;

public interface ISdngData extends IData {

    ActionDoneParser getActionsDone();

    ErrorParser getErrors();
}
