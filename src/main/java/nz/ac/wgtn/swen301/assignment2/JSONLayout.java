package nz.ac.wgtn.swen301.assignment2;

import com.sun.org.apache.xpath.internal.operations.Quo;
import org.apache.log4j.Layout;
import org.apache.log4j.spi.LoggingEvent;
import org.json.JSONObject;

public class JSONLayout extends Layout{
    private StringBuffer buf = new StringBuffer(256);
    private static final String CURL_BEGIN = "{";
    private static final String CURL_END = "}";
    private static final String COLON = ":";
    private static final String COMMA = ",";
    public static final String QUOTES = "\"";
    private int pairCount = 0;

    @Override
    public String format(LoggingEvent loggingEvent) {
        this.buf.setLength(0);
        this.buf.append("{" + Layout.LINE_SEP);
        addPair("logger", loggingEvent.getLoggerName());
        addPair("level", String.valueOf(loggingEvent.getLevel()));
        addPair("starttime", String.valueOf(loggingEvent.timeStamp - LoggingEvent.getStartTime()));
        addPair("thread", loggingEvent.getThreadName());
        addPair("message", loggingEvent.getRenderedMessage());
        this.buf.append("}");
        return this.buf.toString();
    }

    private void addPair(String key, String value){
        if (pairCount > 0){
            this.buf.append(COMMA + Layout.LINE_SEP);
        }
        this.buf.append(QUOTES).append(key).append(QUOTES)
        .append(COLON).append(QUOTES).append(value).append(QUOTES);
        pairCount++;
    }

    @Override
    public boolean ignoresThrowable() {
        return true;
    }

    @Override
    public void activateOptions() {

    }
}
