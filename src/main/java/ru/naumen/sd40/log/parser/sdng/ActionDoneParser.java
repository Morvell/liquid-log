package ru.naumen.sd40.log.parser.sdng;

import ru.naumen.perfhouse.interfaces.IDataParser;
import ru.naumen.sd40.log.parser.IData;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ActionDoneParser implements IDataParser {


    private final Pattern doneRegEx = Pattern.compile("Done\\((\\d+)\\): ?(.*?Action)");

    private static Set<String> EXCLUDED_ACTIONS = new HashSet<>();

    static
    {
        EXCLUDED_ACTIONS.add("EventAction".toLowerCase());
    }

    public void parseLine(IData data , String line)
    {
        Matcher matcher = doneRegEx.matcher(line);

        ActionDoneData actionDoneData = (ActionDoneData) data;

        if (matcher.find())
        {
            String actionInLowerCase = matcher.group(2).toLowerCase();
            if (EXCLUDED_ACTIONS.contains(actionInLowerCase))
            {
                return;
            }

            actionDoneData.setTimes(Integer.parseInt(matcher.group(1)));
            if (actionInLowerCase.equals("addobjectaction"))
            {
                actionDoneData.incAddObjectActions();
            }
            else if (actionInLowerCase.equals("getcatalogsaction")){
                actionDoneData.incGetCatalogsActions();
            }
            else if (actionInLowerCase.equals("editobjectaction"))
            {
                actionDoneData.incEditObjectsActions();
            }
            else if (actionInLowerCase.matches("(?i)[a-zA-Z]+comment[a-zA-Z]+"))
            {
                actionDoneData.incCommentActions();
            }
            else if (!actionInLowerCase.contains("advlist")
                    && actionInLowerCase.matches("(?i)^([a-zA-Z]+|Get)[a-zA-Z]+List[a-zA-Z]+"))

            {
                actionDoneData.incGetListActions();
            }
            else if (actionInLowerCase.matches("(?i)^([a-zA-Z]+|Get)[a-zA-Z]+Form[a-zA-Z]+"))
            {
                actionDoneData.incGetFormActions();
            }
            else if (actionInLowerCase.matches("(?i)^([a-zA-Z]+|Get)[a-zA-Z]+DtObject[a-zA-Z]+"))
            {
                actionDoneData.incGetDtObjectActions();
            }
            else if (actionInLowerCase.matches("(?i)[a-zA-Z]+search[a-zA-Z]+"))
            {
                actionDoneData.incSearchActions();
            }

        }
    }
}
