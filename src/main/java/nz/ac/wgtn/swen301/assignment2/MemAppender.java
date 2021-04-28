package nz.ac.wgtn.swen301.assignment2;

import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.spi.LoggingEvent;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class MemAppender extends AppenderSkeleton implements MemAppenderMBean {
    private final List<LoggingEvent> logs = new LinkedList<>();
    private final long maxSize = 1000;
    private long discardedLogCount = 0;


    public MemAppender() {
    }

    public void exportToJSON(String fileName) {
        try (FileWriter file = new FileWriter(fileName)) {
            JSONLayout layout = new JSONLayout();
            JSONArray jsonArr = new JSONArray();
            for (LoggingEvent event : logs) {
                JSONObject obj = new JSONObject(layout.format(event));
                jsonArr.put(obj);
            }
            file.write(jsonArr.toString(3));
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void append(LoggingEvent loggingEvent) {
        this.logs.add(loggingEvent);
        if (logs.size() > maxSize) {
            logs.remove(0);
            discardedLogCount++;
        }
    }

    @Override
    public void close() {
    }

    @Override
    public boolean requiresLayout() {
        return false;
    }

    public long getMaxSize() {
        return maxSize;
    }

    public List<LoggingEvent> getCurrentLogs() {
        return Collections.unmodifiableList(logs);
    }

    @Override
    public String[] getLogs() {
        String[] strLogs = new String[(int) maxSize];
        PatternLayout layout = new PatternLayout();
        for (int i = 0; i < logs.size(); i++){
            strLogs[i] = layout.format(logs.get(i));
        }
        return strLogs;
    }

    @Override
    public long getLogCount() {
        return logs.size();
    }

    @Override
    public long getDiscardedLogCount() {
        return discardedLogCount;
    }
}
