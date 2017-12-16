package ru.naumen.perfhouse.influx;

import org.influxdb.dto.BatchPoints;
import org.influxdb.dto.QueryResult;
import org.json.JSONObject;
import org.springframework.stereotype.Component;
import ru.naumen.sd40.log.parser.sdng.ActionDoneData;
import ru.naumen.sd40.log.parser.sdng.ErrorData;
import ru.naumen.sd40.log.parser.gc.IGcData;
import ru.naumen.sd40.log.parser.top.ITopData;

import java.util.List;

/**
 * Created by doki on 24.10.16.
 */
@Component
public interface IInfluxDAO
{
    void connectToDB(String dbName);
    void destroy();
    QueryResult.Series executeQuery(String dbName, String query);
    List<String> getDbList();
    void init();
    BatchPoints startBatchPoints(String dbName);
    void storeActionsFromLog(BatchPoints batch, String dbName, long date, ActionDoneData dones,
                             ErrorData errors);
    void storeFromJSon(BatchPoints batch, String dbName, JSONObject data);
    void storeGc(BatchPoints batch, String dbName, long date, IGcData gc);
    void storeTop(BatchPoints batch, String dbName, long date, ITopData data);
    void writeBatch(BatchPoints batch);
}
