package ru.naumen.sd40.log.parser;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.influxdb.dto.BatchPoints;
import ru.naumen.perfhouse.influx.InfluxDAO;
import ru.naumen.perfhouse.interfaces.IDataParser;
import ru.naumen.perfhouse.interfaces.ITimeParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by doki on 22.10.16.
 */
public class Parser
{
    /**
     * @throws IOException
     * @throws ParseException
     */
    public static List<AfterParseLogStat> parse(InfluxDAO influxDAO, ParserDate parserDate) throws IOException, ParseException {
        String influxDb = parserDate.getNameForBD();
        influxDb = influxDb.replaceAll("-", "_");
        influxDAO.init();
        influxDAO.connectToDB(influxDb);

        String finalInfluxDb = influxDb;
        BatchPoints points = influxDAO.startBatchPoints(influxDb);

        List<AfterParseLogStat> logStats = new ArrayList<>();

        HashMap<Long, IDataParser> data;

        switch (parserDate.getParserConf()) {
            case "sdng":
                data = readAndParse("sdng",
                        parserDate.getFilePath().getInputStream(),
                        new SDNGTimeParser(), new SdngDataParser());

                break;
            case "gc":
                data = readAndParse("gc",
                        parserDate.getFilePath().getInputStream(),
                        new GCTimeParser(), new GCDataParser());
                break;
////        case "top":
////            TopParser topParser = new TopParser(parserDate.getFilePath().getOriginalFilename(), data);
////
////            topParser.configureTimeZone(parserDate.getTimeZone());
////
////            //Parse top
////            topParser.parse(parserDate.getFilePath().getInputStream());
////            break;
        default:
            throw new IllegalArgumentException(
                    "Unknown parse mode! Availiable modes: sdng, gc, top. Requested mode: " + parserDate.getParserConf());
        }


            data.forEach((k, set) ->
            {
                switch (parserDate.getParserConf()){
                    case "sdng":
                        SdngDataParser sdng = (SdngDataParser) set;
                        ActionDoneParser dones = sdng.getActionsDone();
                        dones.calculate();
                        ErrorParser erros = sdng.getErrors();
                        if (parserDate.getTraceResult()) {
                            logStats.add(new AfterParseLogStat(dones, k, erros.getErrorCount()));
                        }

                        if (!dones.isNan()) {
                            influxDAO.storeActionsFromLog(points, finalInfluxDb, k, dones, erros);
                        }
                        break;
                    case "gc":
                        GCDataParser gcSet = (GCDataParser) set;
                        GCParser gc = gcSet.getGc();
                        if (!gc.isNan()) {
                            influxDAO.storeGc(points, finalInfluxDb, k, gc);
                        }
                        break;
                    case "top":
                        break;

                }




//            TopData cpuData = set.cpuData();
//            if (!cpuData.isNan())
//            {
//                influxDAO.storeTop(points, finalInfluxDb, k, cpuData);
//            }
            });

        influxDAO.writeBatch(points);
        return logStats;
    }



    private static IDataParser parserFactory(String type){
        switch (type){
            case "sdng":
                return new SdngDataParser();
            case "gc":
                return new GCDataParser();
            case "top":
                return null;
            default:
                throw new IllegalArgumentException(
                        "Unknown parse mode! Availiable modes: sdng, gc, top. Requested mode: " + type);

        }
    }

    private static HashMap<Long, IDataParser> readAndParse(String parserConf, InputStream is, ITimeParser timeParser, IDataParser type) throws IOException, ParseException {

        HashMap<Long, IDataParser> data = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(is)))
        {
            String line;
            while ((line = br.readLine()) != null)
            {
                long time = timeParser.parseTime(line);

                if (time == 0)
                {
                    continue;
                }

                int min5 = 5 * 60 * 1000;
                long count = time / min5;
                long key = count * min5;

                data.computeIfAbsent(key, k -> parserFactory(parserConf)).parseLine(line);
            }
        }
        return data;
    }


}
