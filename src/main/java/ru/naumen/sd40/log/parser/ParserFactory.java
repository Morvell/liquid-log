package ru.naumen.sd40.log.parser;

import ru.naumen.perfhouse.interfaces.IDataParser;
import ru.naumen.sd40.log.parser.gc.GCParser;
import ru.naumen.sd40.log.parser.sdng.SdngDataParser;
import ru.naumen.sd40.log.parser.top.TopParser;

public class ParserFactory {

    public static IDataParser getInstance(String type) {

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

    private ParserFactory() {
    }
}
