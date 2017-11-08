package ru.naumen.sd40.log.parser;

import org.influxdb.dto.BatchPoints;
import ru.naumen.perfhouse.influx.InfluxDAO;
import ru.naumen.perfhouse.interfaces.ITimeParser;

import java.io.BufferedReader;
import java.io.IOException;
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
    public static List<AfterParseLogStat> parse(InfluxDAO influxDAO, ParserDate parserDate) throws IOException, ParseException
    {
        String influxDb = parserDate.getNameForBD();
        influxDb = influxDb.replaceAll("-", "_");
        influxDAO.init();
        influxDAO.connectToDB(influxDb);

        String finalInfluxDb = influxDb;
        BatchPoints points = influxDAO.startBatchPoints(influxDb);

        HashMap<Long, DataSet> data = new HashMap<>();

        ITimeParser sdngTimeParser = new SDNGTimeParser(parserDate.getTimeZone());
        ITimeParser gcTime = new GCTimeParser(parserDate.getTimeZone());
        
        switch (parserDate.getParserConf())
        {
        case "sdng":
            //Parse sdng
            try (BufferedReader br = new BufferedReader(new InputStreamReader(parserDate.getFilePath().getInputStream())))
            {
                String line;
                while ((line = br.readLine()) != null)
                {
                    long time = sdngTimeParser.parseTime(line);

                    if (time == 0)
                    {
                        continue;
                    }

                    int min5 = 5 * 60 * 1000;
                    long count = time / min5;
                    long key = count * min5;

                    data.computeIfAbsent(key, k -> new DataSet()).parseLine(line);
                }
            }
            break;
        case "gc":
            //Parse gc log
            try (BufferedReader br = new BufferedReader(new InputStreamReader(parserDate.getFilePath().getInputStream())))
            {
                String line;
                while ((line = br.readLine()) != null)
                {
                    long time = gcTime.parseTime(line);

                    if (time == 0)
                    {
                        continue;
                    }

                    int min5 = 5 * 60 * 1000;
                    long count = time / min5;
                    long key = count * min5;
                    data.computeIfAbsent(key, k -> new DataSet()).parseGcLine(line);
                }
            }
            break;
        case "top":
            TopParser topParser = new TopParser(parserDate.getFilePath().getOriginalFilename(), data);

            topParser.configureTimeZone(parserDate.getTimeZone());

            //Parse top
            topParser.parse(parserDate.getFilePath().getInputStream());
            break;
        default:
            throw new IllegalArgumentException(
                    "Unknown parse mode! Availiable modes: sdng, gc, top. Requested mode: " + parserDate.getParserConf());
        }

        List<AfterParseLogStat> logStats = new ArrayList<>();

        data.forEach((k, set) ->
        {
            ActionDoneParser dones = set.getActionsDone();
            dones.calculate();
            ErrorParser erros = set.getErrors();
            if(parserDate.getTraceResult()) { logStats.add(new AfterParseLogStat(dones, k, erros.getErrorCount()));}

            if (!dones.isNan())
            {
                influxDAO.storeActionsFromLog(points, finalInfluxDb, k, dones, erros);
            }

            GCParser gc = set.getGc();
            if (!gc.isNan())
            {
                influxDAO.storeGc(points, finalInfluxDb, k, gc);
            }

            TopData cpuData = set.cpuData();
            if (!cpuData.isNan())
            {
                influxDAO.storeTop(points, finalInfluxDb, k, cpuData);
            }
        });
        influxDAO.writeBatch(points);
    return logStats;
    }
}
