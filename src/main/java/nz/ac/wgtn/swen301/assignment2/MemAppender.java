package nz.ac.wgtn.swen301.assignment2;

import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.Layout;
import org.apache.log4j.spi.LoggingEvent;
import org.json.JSONArray;
import org.json.JSONObject;
import sun.rmi.runtime.Log;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class MemAppender extends AppenderSkeleton {
    private List<LoggingEvent> logs = new LinkedList<>();
    private long maxSize = 1000;
    private long discardedLogCount = 0;


    public MemAppender(){}

    public MemAppender(Layout layout) throws IOException {
        this.layout = layout;
    }

    public void exportToJSON(String fileName) throws IOException{
        FileWriter file = new FileWriter(fileName);
        JSONLayout layout = new JSONLayout();
        JSONArray jsonArr = new JSONArray();
        for (LoggingEvent event: logs){
            JSONObject obj = new JSONObject(layout.format(event));
            jsonArr.put(obj);
        }
        file.write(jsonArr.toString());
        file.flush();
    }

    @Override
    protected void append(LoggingEvent loggingEvent) {
        if (logs.size() >= maxSize) {
            logs.remove(0);
            discardedLogCount++;
        }
        this.logs.add(loggingEvent);
    }

    @Override
    public void close() {

    }

    @Override
    public boolean requiresLayout() {
        return false;
    }

    public List<LoggingEvent> getCurrentLogs() {
        return Collections.unmodifiableList(logs);
    }

    public long getDiscardedLogCount() {
        return discardedLogCount;
    }
}
