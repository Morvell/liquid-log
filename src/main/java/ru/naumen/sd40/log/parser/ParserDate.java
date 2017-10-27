package ru.naumen.sd40.log.parser;

import org.springframework.web.multipart.MultipartFile;

public class ParserDate {
    private String nameForBD;
    private String parserConf;
    private MultipartFile filePath;
    private String timeZone;
    private Boolean traceResult;

    public String getNameForBD() {
        return nameForBD;
    }

    public void setNameForBD(String nameForBD) {
        this.nameForBD = nameForBD;
    }

    public String getParserConf() {
        return parserConf;
    }

    public void setParserConf(String parserConf) {
        this.parserConf = parserConf;
    }

    public MultipartFile getFilePath() {
        return filePath;
    }

    public void setFilePath(MultipartFile filePath) {
        this.filePath = filePath;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    public Boolean getTraceResult() {
        return traceResult;
    }

    public void setTraceResult(Boolean traceResult) {
        this.traceResult = traceResult;
    }
}