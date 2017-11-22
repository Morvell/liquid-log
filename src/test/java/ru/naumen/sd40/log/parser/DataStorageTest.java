package ru.naumen.sd40.log.parser;

import org.influxdb.dto.BatchPoints;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.naumen.perfhouse.influx.IInfluxDAO;
import ru.naumen.perfhouse.influx.InfluxDAO;
import ru.naumen.perfhouse.interfaces.IDataParser;
import ru.naumen.sd40.log.parser.gc.GCParser;
import ru.naumen.sd40.log.parser.sdng.SdngDataParser;
import ru.naumen.sd40.log.parser.top.TopParser;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class DataStorageTest {

    private IInfluxDAO influxDAOMock;
    private DataStorage dataStorage;
    private BatchPoints batchPoints;
    private final static String dbName= "logTest";

    @Before
    public void initializationBeforeEachTest()
    {
        influxDAOMock = mock(IInfluxDAO.class);
        batchPoints = BatchPoints.database(dbName).build();
        when(influxDAOMock.startBatchPoints(dbName)).thenReturn(batchPoints);
        dataStorage = new DataStorage(influxDAOMock);
        dataStorage.init(dbName, null);
    }

    @Test
    public void zeroValue() {
        dataStorage.setParserType("sdng");
        //given

        //when
        IDataParser dataSet = dataStorage.get(0);

        //then
        Assert.assertNotNull(dataSet);
    }

    @Test
    public void noneZeroValue() {
        dataStorage.setParserType("sdng");
        //given

        //when
        IDataParser dataSet = dataStorage.get(5);

        //then
        Assert.assertNotNull(dataSet);
    }

    @Test
    public void bigNoneZeroValue() {
        dataStorage.setParserType("sdng");
        //given

        //when
        IDataParser dataSet = dataStorage.get(1023502352);

        //then
        Assert.assertNotNull(dataSet);
    }

    @Test
    public void identifyValue() {
        dataStorage.setParserType("sdng");
        //given

        //when
        IDataParser dataSet = dataStorage.get(0);
        IDataParser dataSetTwo = dataStorage.get(0);

        //then
        Assert.assertEquals(dataSet,dataSetTwo);
    }

    @Test
    public void noIdentifyValue() {
        dataStorage.setParserType("sdng");
        //given

        //when
        IDataParser dataSet = dataStorage.get(0);
        IDataParser dataSetTwo = dataStorage.get(1);

        //then
        Assert.assertNotEquals(dataSet,dataSetTwo);
    }

    @Test
    public void sdngTest() {
        dataStorage.setParserType("sdng");
        //given
        String line = "68599820 [GetDtObjectsForListAction naumen #295 192.168.112.77 naumen] (07 сен 2017 00:01:31,792) INFO  dispatch.Dispatch - SQL(0) Done(14):GetDtObjectsForListAction [isCheckAttrPermissions()=false, getDtoCriteria()=select team@metaClass, abstractBO@title, team@recipientAgreements, removed, removalDate, team@computed, team@composite, team@edited from team where removed = false order by abstractBO@title ASC limit 20, getMappingContextStrategies()=[mappingContextEmulateReferencesStrategy, mappingContextCutImagesStrategy, mappingContextCutTextStrategy], isFolder()=false, getTimeZoneOffset()=0, isCountable()=false, getPermissions()=[], ]\n";

        //when
        SdngDataParser dataSet = (SdngDataParser) dataStorage.get(0);
        dataSet.parseLine(line);
        dataStorage.get(1);

        //then
        verify(influxDAOMock).storeActionsFromLog(batchPoints, dbName, 0,
                dataSet.getActionsDone(), dataSet.getErrors());

    }

    @Test
    public void sdngTestForTwo() {
        dataStorage.setParserType("sdng");
        //given
        String line = "68599820 [GetDtObjectsForListAction naumen #295 192.168.112.77 naumen] (07 сен 2017 00:01:31,792) INFO  dispatch.Dispatch - SQL(0) Done(14):GetDtObjectsForListAction [isCheckAttrPermissions()=false, getDtoCriteria()=select team@metaClass, abstractBO@title, team@recipientAgreements, removed, removalDate, team@computed, team@composite, team@edited from team where removed = false order by abstractBO@title ASC limit 20, getMappingContextStrategies()=[mappingContextEmulateReferencesStrategy, mappingContextCutImagesStrategy, mappingContextCutTextStrategy], isFolder()=false, getTimeZoneOffset()=0, isCountable()=false, getPermissions()=[], ]\n";

        //when
        SdngDataParser dataSet = (SdngDataParser) dataStorage.get(0);
        dataSet.parseLine(line);
        SdngDataParser dataSetTwo = (SdngDataParser) dataStorage.get(1);
        dataSetTwo.parseLine(line);

        //then
        verify(influxDAOMock,times(1)).storeActionsFromLog(batchPoints, dbName, 0,
                dataSet.getActionsDone(), dataSet.getErrors());

    }

    @Test
    public void gcTest() {
        dataStorage.setParserType("gc");
        //given
        String line = "2017-11-03T10:41:48.938+0000: 51.763: [GC (Allocation Failure) [PSYoungGen: 544439K->66764K(679936K)] 657453K->179778K(2427904K), 0.0441722 secs] [Times: user=0.06 sys=0.01, real=0.05 secs]";

        //when
        GCParser dataSet = (GCParser) dataStorage.get(0);
        dataSet.parseLine(line);
        dataStorage.get(1);

        //then
        verify(influxDAOMock).storeGc(batchPoints, dbName, 0, dataSet);

    }

    @Test
    public void gcTestForTwo() {
        dataStorage.setParserType("gc");
        //given
        String line = "2017-11-03T10:41:48.938+0000: 51.763: [GC (Allocation Failure) [PSYoungGen: 544439K->66764K(679936K)] 657453K->179778K(2427904K), 0.0441722 secs] [Times: user=0.06 sys=0.01, real=0.05 secs]";

        //when
        GCParser dataSet = (GCParser) dataStorage.get(0);
        dataSet.parseLine(line);
        GCParser dataSetTwo = (GCParser) dataStorage.get(1);
        dataSetTwo.parseLine(line);

        //then
        verify(influxDAOMock,times(1)).storeGc(batchPoints, dbName, 0, dataSet);

    }

    @Test
    public void topTest() {
        dataStorage.setParserType("top");
        //given
        String line = "18321 adminis+  20   0 4472716 3.214g      0  24476 S  13.3 83.3 281:21.96 java";

        //when
        TopParser dataSet = (TopParser) dataStorage.get(0);
        dataSet.parseLine(line);
        dataStorage.get(1);

        //then
        verify(influxDAOMock).storeTop(batchPoints, dbName, 0, dataSet.getCpuData());

    }

    @Test
    public void topTestForTwo() {
        dataStorage.setParserType("top");
        //given
        String line = "18321 adminis+  20   0 4472716 3.214g      0  24476 S  13.3 83.3 281:21.96 java";

        //when
        TopParser dataSet = (TopParser) dataStorage.get(0);
        dataSet.parseLine(line);
        TopParser dataSetTwo = (TopParser) dataStorage.get(1);
        dataSetTwo.parseLine(line);
        dataStorage.save();

        //then
        verify(influxDAOMock,times(1)).storeTop(batchPoints, dbName, 0, dataSet.getCpuData());
        verify(influxDAOMock,times(1)).storeTop(batchPoints, dbName, 1, dataSetTwo.getCpuData());

    }


}