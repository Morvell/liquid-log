package ru.naumen.sd40.log.parser;

import ru.naumen.perfhouse.interfaces.IDataParser;
import ru.naumen.sd40.log.parser.gc.GCParser;
import ru.naumen.sd40.log.parser.gc.GcData;
import ru.naumen.sd40.log.parser.sdng.SdngData;
import ru.naumen.sd40.log.parser.sdng.SdngDataParser;
import ru.naumen.sd40.log.parser.top.TopData;
import ru.naumen.sd40.log.parser.top.TopParser;

class DataFactory {

    static IData getInstance(String type) {

        switch (type){
            case "sdng":
                return new SdngData();
            case "gc":
                return new GcData();
            case "top":
                return new TopData();
            default:
                throw new IllegalArgumentException(
                        "Unknown parse mode! Availiable modes: sdng, gc, top. Requested mode: " + type);
        }
    }

    private DataFactory() {
    }
}
