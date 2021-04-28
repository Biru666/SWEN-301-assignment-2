package nz.ac.wgtn.swen301.assignment2;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import static org.junit.Assert.*;

public class MemAppenderTest {

    //helper method
    public JSONArray readJSON(String filename) throws IOException{
        String contents = new String((Files.readAllBytes(Paths.get(filename))));
        return new JSONArray(contents);
    }

    @Test
    public void test1() throws IOException {
        BasicConfigurator.configure();
        Logger logger = Logger.getLogger("foo");
        MemAppender memApp = new MemAppender();
        logger.addAppender(memApp);
        logger.setLevel(Level.WARN);
        logger.warn("something went wrong");

        logger.setLevel(Level.DEBUG);
        logger.debug("interesting !");

        memApp.exportToJSON("logs.json");

        JSONArray jsonLogs = readJSON("logs.json");

        JSONObject obj1 = jsonLogs.getJSONObject(0);
        JSONObject obj2 = jsonLogs.getJSONObject(1);

        assertEquals("WARN", obj1.getString("level"));
        assertEquals("DEBUG", obj2.getString("level"));

        assertEquals("something went wrong", obj1.getString("message"));
        assertEquals("interesting !", obj2.getString("message"));
    }

    @Test
    public void test2(){
        BasicConfigurator.configure();
        Logger logger = Logger.getLogger("foo");
        MemAppender memApp = new MemAppender();
        logger.addAppender(memApp);
        logger.setLevel(Level.WARN);
        for (int i = 0; i < 2000; i++) {
            logger.warn("something went wrong");
        }

        assertTrue(memApp.getCurrentLogs().size() <= memApp.getMaxSize());
    }
}
