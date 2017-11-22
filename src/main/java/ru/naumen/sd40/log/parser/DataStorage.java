package ru.naumen.sd40.log.parser;

import org.influxdb.dto.BatchPoints;
import ru.naumen.perfhouse.influx.IInfluxDAO;
import ru.naumen.perfhouse.influx.InfluxDAO;
import ru.naumen.perfhouse.interfaces.IDataParser;
import ru.naumen.sd40.log.parser.gc.GCParser;
import ru.naumen.sd40.log.parser.sdng.SdngDataParser;
import ru.naumen.sd40.log.parser.top.TopParser;

public class DataStorage {
    private IInfluxDAO influxDAO;
    private BatchPoints batchPoints;
    private String dbName;
    private long currentKey;
    private IDataParser dataSet;

    private String parserType;

    public DataStorage(IInfluxDAO influxDAO) {
        this.influxDAO = influxDAO;
    }

    public IDataParser get(long key) {
        if (dataSet != null)
        {
            if (key == currentKey) {
                return dataSet;
            }
            store(dataSet);
        }
        currentKey = key;
        dataSet = ParserFactory.getInstance(parserType);
        return dataSet;
    }

    public void init(String dbName, String parserType) {
        this.dbName = dbName.replaceAll("-", "_");
        this.parserType = parserType;
        influxDAO.connectToDB(this.dbName);
        batchPoints = influxDAO.startBatchPoints(this.dbName);
    }

    public void save()
    {
        store(dataSet);
    }

    private void store(IDataParser dataSet) {

        switch (parserType) {
            case "sdng":
                SdngDataParser sdng = (SdngDataParser) dataSet;
                ActionDoneParser dones = sdng.getActionsDone();
                dones.calculate();
                ErrorParser erros = sdng.getErrors();

                if (!dones.isNan())
                    influxDAO.storeActionsFromLog(batchPoints, dbName, currentKey, dones, erros);
                break;

            case "gc":
                GCParser gc = (GCParser) dataSet;
                if (!gc.isNan()) {
                    influxDAO.storeGc(batchPoints, dbName, currentKey, gc);
                }
                break;

            case "top":
                TopParser topSet = (TopParser) dataSet;
                TopData cpuData = topSet.getCpuData();
                if (!cpuData.isNan())
                    influxDAO.storeTop(batchPoints, dbName, currentKey, cpuData);
                break;
        }

        influxDAO.writeBatch(batchPoints);
    }

    public void setParserType(String parserType) {
        this.parserType = parserType;
    }
}
