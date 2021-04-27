package nz.ac.wgtn.swen301.assignment2;

import org.apache.log4j.*;
import org.apache.log4j.spi.LoggingEvent;
import org.json.JSONObject;
import org.junit.Test;
import static org.junit.Assert.*;

public class JSONLayoutTest {
    @Test
    public void test1(){
        BasicConfigurator.configure();
        Logger logger = Logger.getLogger("foo");
        logger.setLevel(Level.WARN);
        JSONLayout lay = new JSONLayout();
        logger.addAppender(new ConsoleAppender(lay));
        logger.error("something went wrong");

        LoggingEvent event = lay.event;
        String jsonOutput = lay.format(event);
        JSONObject jsonObj = new JSONObject(jsonOutput);

        assertEquals(event.getLoggerName(), jsonObj.getString("logger"));
        assertEquals(event.getLevel().toString(), jsonObj.getString("level"));
        assertEquals(Long.toString(LoggingEvent.getStartTime()), jsonObj.getString("starttime"));
        assertEquals(event.getThreadName(), jsonObj.getString("thread"));
        assertEquals(event.getMessage(), jsonObj.getString("message"));
    }
}
