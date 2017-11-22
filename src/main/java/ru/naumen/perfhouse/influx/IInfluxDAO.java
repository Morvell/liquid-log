package ru.naumen.perfhouse.influx;

import static ru.naumen.perfhouse.statdata.Constants.GarbageCollection.AVARAGE_GC_TIME;
import static ru.naumen.perfhouse.statdata.Constants.GarbageCollection.GCTIMES;
import static ru.naumen.perfhouse.statdata.Constants.GarbageCollection.MAX_GC_TIME;
import static ru.naumen.perfhouse.statdata.Constants.PerformedActions.*;
import static ru.naumen.perfhouse.statdata.Constants.ResponseTimes.COUNT;
import static ru.naumen.perfhouse.statdata.Constants.ResponseTimes.ERRORS;
import static ru.naumen.perfhouse.statdata.Constants.ResponseTimes.MAX;
import static ru.naumen.perfhouse.statdata.Constants.ResponseTimes.MEAN;
import static ru.naumen.perfhouse.statdata.Constants.ResponseTimes.PERCENTILE50;
import static ru.naumen.perfhouse.statdata.Constants.ResponseTimes.PERCENTILE95;
import static ru.naumen.perfhouse.statdata.Constants.ResponseTimes.PERCENTILE99;
import static ru.naumen.perfhouse.statdata.Constants.ResponseTimes.PERCENTILE999;
import static ru.naumen.perfhouse.statdata.Constants.ResponseTimes.STDDEV;
import static ru.naumen.perfhouse.statdata.Constants.Top.AVG_CPU;
import static ru.naumen.perfhouse.statdata.Constants.Top.AVG_LA;
import static ru.naumen.perfhouse.statdata.Constants.Top.AVG_MEM;
import static ru.naumen.perfhouse.statdata.Constants.Top.MAX_CPU;
import static ru.naumen.perfhouse.statdata.Constants.Top.MAX_LA;
import static ru.naumen.perfhouse.statdata.Constants.Top.MAX_MEM;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.BatchPoints;
import org.influxdb.dto.Point;
import org.influxdb.dto.Point.Builder;
import org.influxdb.dto.Query;
import org.influxdb.dto.QueryResult;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import ru.naumen.perfhouse.statdata.Constants;
import ru.naumen.sd40.log.parser.ActionDoneParser;
import ru.naumen.sd40.log.parser.ErrorParser;
import ru.naumen.sd40.log.parser.gc.GCParser;
import ru.naumen.sd40.log.parser.TopData;

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
    void storeActionsFromLog(BatchPoints batch, String dbName, long date, ActionDoneParser dones,
                             ErrorParser errors);
    void storeFromJSon(BatchPoints batch, String dbName, JSONObject data);
    void storeGc(BatchPoints batch, String dbName, long date, GCParser gc);
    void storeTop(BatchPoints batch, String dbName, long date, TopData data);
    void writeBatch(BatchPoints batch);
}
