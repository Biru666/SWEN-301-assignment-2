package nz.ac.wgtn.swen301.assignment2;

import org.apache.log4j.Layout;
import org.apache.log4j.spi.LoggingEvent;
import org.json.JSONObject;
import org.json.JSONPointer;

public class JSONLayout extends Layout{
    public LoggingEvent event;
    private StringBuffer buf = new StringBuffer(256);

    @Override
    public String format(LoggingEvent loggingEvent) {
        this.event = loggingEvent;
        this.buf.setLength(0);
        JSONObject json = new JSONObject();
        json.put("logger", loggingEvent.getLoggerName());
        json.put("level", String.valueOf(loggingEvent.getLevel()));
        json.put("starttime", String.valueOf(LoggingEvent.getStartTime()));
        json.put("thread", loggingEvent.getThreadName());
        json.put("message", loggingEvent.getRenderedMessage());
        this.buf.append(json.toString(3));
        return this.buf.toString();
    }

    @Override
    public boolean ignoresThrowable() {
        return true;
    }

    @Override
    public void activateOptions() {

    }
}
