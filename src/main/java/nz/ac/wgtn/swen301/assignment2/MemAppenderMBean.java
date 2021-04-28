package nz.ac.wgtn.swen301.assignment2;

import java.io.IOException;

public interface MemAppenderMBean {
    String[] getLogs();
    long getLogCount();
    long getDiscardedLogCount();
    void exportToJSON(String fileName);
}
