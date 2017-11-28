package ru.naumen.sd40.log.parser;

import org.springframework.stereotype.Component;
import ru.naumen.perfhouse.influx.InfluxDAO;
import ru.naumen.perfhouse.interfaces.IDataParser;
import ru.naumen.perfhouse.interfaces.ITimeParser;
import ru.naumen.sd40.log.parser.gc.GCTimeParser;
import ru.naumen.sd40.log.parser.sdng.SdngTimeParser;
import ru.naumen.sd40.log.parser.top.TopTimeParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;

@Component
public class Parser
{
    /**
     * @throws IOException
     * @throws ParseException
     */
    private DataStorage dataStorage;

    public void  parse(InfluxDAO influxDAO, ParserDate parserDate) throws IOException, ParseException {
        dataStorage = new DataStorage(influxDAO);
        dataStorage.init(parserDate.getNameForBD(), parserDate.getParserConf());


        switch (parserDate.getParserConf()) {
            case "sdng":
                readAndParse(
                        parserDate.getFilePath().getInputStream(),
                        new SdngTimeParser(parserDate.getTimeZone()));
                break;
            case "gc":
                readAndParse(
                        parserDate.getFilePath().getInputStream(),
                        new GCTimeParser(parserDate.getTimeZone()));
                break;
            case "top":
                readAndParse(
                        parserDate.getFilePath().getInputStream(),
                        new TopTimeParser(parserDate.getFilePath().getOriginalFilename(), parserDate.getTimeZone())
                );
                break;
        default:
            throw new IllegalArgumentException(
                    "Unknown parse mode! Availiable modes: sdng, gc, top. Requested mode: " + parserDate.getParserConf());
        }

    }


    private void readAndParse(InputStream is, ITimeParser timeParser) throws IOException, ParseException {

        try (BufferedReader br = new BufferedReader(new InputStreamReader(is)))
        {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.isEmpty()) continue;
                long time = timeParser.parseTime(line);

                if (time == 0) {
                    continue;
                }

                int min5 = 5 * 60 * 1000;
                long count = time / min5;
                long key = count * min5;

                IDataParser dataSet = dataStorage.get(key);
                dataSet.parseLine(line);
            }
            dataStorage.save();
        }

    }


}
