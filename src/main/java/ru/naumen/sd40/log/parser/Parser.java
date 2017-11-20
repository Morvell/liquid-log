package ru.naumen.sd40.log.parser;

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
import java.util.Objects;

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
                        new SDNGTimeParser(parserDate.getTimeZone()));

                break;
            case "gc":
                data = readAndParse("gc",
                        parserDate.getFilePath().getInputStream(),
                        new GCTimeParser(parserDate.getTimeZone()));
                break;
            case "top":
                data = readAndParse("top",
                        parserDate.getFilePath().getInputStream(),
                        new TopTimeParser(parserDate.getFilePath().getOriginalFilename(), parserDate.getTimeZone())
                );
                break;
        default:
            throw new IllegalArgumentException(
                    "Unknown parse mode! Availiable modes: sdng, gc, top. Requested mode: " + parserDate.getParserConf());
        }
            data.forEach((k, set) ->
                    {
                        switch (parserDate.getParserConf()) {
                            case "sdng":
                                SdngDataParser sdng = (SdngDataParser) set;
                                ActionDoneParser dones = sdng.getActionsDone();
                                dones.calculate();
                                ErrorParser erros = sdng.getErrors();
                                if (parserDate.getTraceResult())
                                    logStats.add(new AfterParseLogStat(dones, k, erros.getErrorCount()));

                                if (!dones.isNan())
                                    influxDAO.storeActionsFromLog(points, finalInfluxDb, k, dones, erros);
                                break;

                            case "gc":
                                GCParser gc = (GCParser) set;
                                if (!gc.isNan()) {
                                    influxDAO.storeGc(points, finalInfluxDb, k, gc);
                                }
                                break;

                            case "top":
                                TopParser topSet = (TopParser) set;
                                TopData cpuData = topSet.getCpuData();
                                if (!cpuData.isNan())
                                    influxDAO.storeTop(points, finalInfluxDb, k, cpuData);
                                break;
                }

            });

        influxDAO.writeBatch(points);
        return logStats;
    }

    private static IDataParser parserFactory(String type){
        switch (type){
            case "sdng":
                return new SdngDataParser();
            case "gc":
                return new GCParser();
            case "top":
                return new TopParser();
            default:
                throw new IllegalArgumentException(
                        "Unknown parse mode! Availiable modes: sdng, gc, top. Requested mode: " + type);
        }
    }


    private static HashMap<Long, IDataParser> readAndParse(String parserConf, InputStream is, ITimeParser timeParser) throws IOException, ParseException {

        HashMap<Long, IDataParser> data = new HashMap<>();
        IDataParser currentSet = null;
        try (BufferedReader br = new BufferedReader(new InputStreamReader(is)))
        {
            String line;
            while ((line = br.readLine()) != null)
            {
                if (line.isEmpty()) continue;
                long time = timeParser.parseTime(line);

                if (time != 0)
                {
                    int min5 = 5 * 60 * 1000;
                    long count = time / min5;
                    long key = count * min5;

                    currentSet = data.computeIfAbsent(key, k -> parserFactory(parserConf));
                }
                if (currentSet != null && timeParser.isParse())
                {
                    currentSet.parseLine(line);
                }
            }
        }
        return data;
    }


}
