package nz.ac.wgtn.swen301.assignment2;

import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.Layout;
import org.apache.log4j.spi.LoggingEvent;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class MemAppender extends AppenderSkeleton {
    private final List<LoggingEvent> Logs = new LinkedList<LoggingEvent>();
    private long maxSize = 1000;
    private final long discardedLogCount = 0;


    public MemAppender(){}

    public MemAppender(Layout layout , String filename) throws IOException {
        this.layout = layout;
        this.name = filename;
    }

    @Override
    protected void append(LoggingEvent loggingEvent) {

    }

    @Override
    public void close() {

    }

    @Override
    public boolean requiresLayout() {
        return false;
    }

    public List<LoggingEvent> getCurrentLogs() {
        return Logs;
    }

    public long getDiscardedLogCount() {
        return discardedLogCount;
    }
}
