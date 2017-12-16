//package ru.naumen.sd40.log.parser;
//
//import ru.naumen.sd40.log.parser.gc.GCParser;
//import ru.naumen.sd40.log.parser.top.TopData;
//
///**
// * Created by doki on 22.10.16.
// */
//public class DataSet
//{
//    private ActionDoneData actionsDone;
//    private ErrorData errors;
//    private GCParser gc;
//    private TopData cpuData;
//
//    public DataSet()
//    {
//        actionsDone = new ActionDoneData();
//        errors = new ErrorData();
//        gc = new GCParser();
//        cpuData = new TopData();
//    }
//
//    public void parseLine(String line)
//    {
//        errors.parseLine(line);
//        actionsDone.parseLine(line);
//    }
//
//    public void parseGcLine(String line)
//    {
//        gc.parseLine(line);
//    }
//
//    public ActionDoneData getActionsDone()
//    {
//        return actionsDone;
//    }
//
//    public ErrorData getErrors()
//    {
//        return errors;
//    }
//
//    public GCParser getGc()
//    {
//        return gc;
//    }
//
//    public TopData cpuData()
//    {
//        return cpuData;
//    }
//}
