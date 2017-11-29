package ru.naumen.sd40.log.parser;

import org.influxdb.dto.BatchPoints;
import ru.naumen.perfhouse.influx.IInfluxDAO;
import ru.naumen.perfhouse.interfaces.IDataParser;
import ru.naumen.sd40.log.parser.gc.GCParser;
import ru.naumen.sd40.log.parser.gc.IGcData;
import ru.naumen.sd40.log.parser.sdng.ISdngData;
import ru.naumen.sd40.log.parser.sdng.SdngDataParser;
import ru.naumen.sd40.log.parser.top.ITopData;
import ru.naumen.sd40.log.parser.top.TopData;
import ru.naumen.sd40.log.parser.top.TopParser;

public class DataStorage {
    private IInfluxDAO influxDAO;
    private BatchPoints batchPoints;
    private String dbName;
    private long currentKey;
    private IData dataSet;

    private String parserType;

    public DataStorage(IInfluxDAO influxDAO) {
        this.influxDAO = influxDAO;
    }

    public IData get(long key) {
        if (dataSet != null)
        {
            if (key == currentKey) {
                return dataSet;
            }
            store(dataSet);
        }
        currentKey = key;
        dataSet = DataFactory.getInstance(parserType);
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
        influxDAO.writeBatch(batchPoints);
    }

    private void store(IData dataSet) {

        switch (parserType) {
            case "sdng":
                ISdngData sdng = (ISdngData) dataSet;
                ActionDoneParser dones = sdng.getActionsDone();
                dones.calculate();
                ErrorParser erros = sdng.getErrors();

                if (!dones.isNan())
                    influxDAO.storeActionsFromLog(batchPoints, dbName, currentKey, dones, erros);
                break;

            case "gc":
                IGcData gc = (IGcData) dataSet;
                if (!gc.isNan()) {
                    influxDAO.storeGc(batchPoints, dbName, currentKey, gc);
                }
                break;

            case "top":
                ITopData topSet = (ITopData) dataSet;
                if (!topSet.isNan())
                    influxDAO.storeTop(batchPoints, dbName, currentKey, topSet);
                break;
        }


    }

    public void setParserType(String parserType) {
        this.parserType = parserType;
    }
}
