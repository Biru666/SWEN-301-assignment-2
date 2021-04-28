package nz.ac.wgtn.swen301.assignment2.example;


import nz.ac.wgtn.swen301.assignment2.MemAppender;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import java.lang.management.ManagementFactory;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class LogRunner {
    public static final ScheduledThreadPoolExecutor schedulerLogs = new ScheduledThreadPoolExecutor(1);
    public static final List<Integer> levels = new ArrayList<>(Arrays.asList(-2147483648, 5000, 10000, 20000, 30000, 40000, 50000, 2147483647));

    private static final class MainLoop implements Runnable{
        private int attempt = 1;
        MemAppender app;

        public MainLoop(MemAppender mbean) {
            this.app = mbean;
        }

        @Override
        public void run() {
            if (attempt > 120) schedulerLogs.shutdown();
           schedulerLogs.scheduleAtFixedRate(()->{
               if(!schedulerLogs.getQueue().isEmpty()){System.out.println("Skipping a frame");return;}
               BasicConfigurator.configure();
               Logger logger = Logger.getLogger("foo");
               logger.addAppender(app);
               Random r = new Random();
               logger.log(Level.toLevel(levels.get(r.nextInt(7))), String.valueOf(r.nextInt()));
               attempt++;
           },
              0,1, TimeUnit.SECONDS);
        }
    }

    public static void main(String[] args) throws Exception {
        MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
        ObjectName name = new ObjectName("nz.ac.wgtn.swen301.assignment2.example" +
                ":type=MemAppender");
        MemAppender mbean = new MemAppender();
        mbs.registerMBean(mbean, name);

        MainLoop mainLoop = new MainLoop(mbean);
        mainLoop.app.exportToJSON("LogRunner.json");
    }
}
