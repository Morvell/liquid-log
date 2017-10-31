package ru.naumen.sd40.log.parser;

import org.influxdb.dto.BatchPoints;
import org.springframework.web.multipart.MultipartFile;
import ru.naumen.perfhouse.influx.InfluxDAO;
import ru.naumen.sd40.log.parser.GCParser.GCTimeParser;

import java.io.BufferedReader;
import java.io.FileReader;
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
    public static List<AfterParseLogStat> parse(InfluxDAO influxDAO, String nameBD, String parserConf, MultipartFile filePath, String timeZone) throws IOException, ParseException
    {
        String influxDb = nameBD;
        influxDb = influxDb.replaceAll("-", "_");
        influxDAO.init();
        influxDAO.connectToDB(influxDb);

        String finalInfluxDb = influxDb;
        BatchPoints points = influxDAO.startBatchPoints(influxDb);;


        HashMap<Long, DataSet> data = new HashMap<>();

        TimeParser timeParser = new TimeParser(timeZone);
        GCTimeParser gcTime = new GCTimeParser(timeZone);

        switch (parserConf)
        {
        case "sdng":
            //Parse sdng
            try (BufferedReader br = new BufferedReader(new InputStreamReader(filePath.getInputStream())))
            {
                String line;
                while ((line = br.readLine()) != null)
                {
                    long time = timeParser.parseLine(line);

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
            try (BufferedReader br = new BufferedReader(new FileReader(filePath.getName())))
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
            TopParser topParser = new TopParser(filePath.getName(), data);

            topParser.configureTimeZone(timeZone);

            //Parse top
            topParser.parse();
            break;
        default:
            throw new IllegalArgumentException(
                    "Unknown parse mode! Availiable modes: sdng, gc, top. Requested mode: " + parserConf);
        }

        if (System.getProperty("NoCsv") == null)
        {
            System.out.print("Timestamp;Actions;Min;Mean;Stddev;50%%;95%%;99%%;99.9%%;Max;Errors\n");
        }

        List<AfterParseLogStat> logStats = new ArrayList<>();
        data.forEach((k, set) ->
        {
            ActionDoneParser dones = set.getActionsDone();
            dones.calculate();
            ErrorParser erros = set.getErrors();
            logStats.add(new AfterParseLogStat(dones, k, erros.getErrorCount()));
            if (System.getProperty("NoCsv") == null)
            {
                System.out.print(String.format("%d;%d;%f;%f;%f;%f;%f;%f;%f;%f;%d\n", k, dones.getCount(),
                        dones.getMin(), dones.getMean(), dones.getStddev(), dones.getPercent50(), dones.getPercent95(),
                        dones.getPercent99(), dones.getPercent999(), dones.getMax(), erros.getErrorCount()));
            }
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
