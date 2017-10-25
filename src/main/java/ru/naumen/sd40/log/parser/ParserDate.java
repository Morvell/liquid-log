package ru.naumen.sd40.log.parser;

public class ParserDate {
    private String nameForBD;
    private String parserConf;
    private String filePath;
    private String timeZone;
    private Boolean traceResult;

    public ParserDate(){
        setNameForBD("Unset");
        setParserConf("Unset");
        setFilePath("Unset");
        setTimeZone("Unset");
        setTraceResult(false);
    }

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

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
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