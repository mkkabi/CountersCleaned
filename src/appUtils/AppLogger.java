package appUtils;

import static appUtils.NIO.appHome;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.util.logging.StreamHandler;

// Setting up loggers Logger.getLogger
public class AppLogger{

    private static volatile AppLogger instance = null;
    private final static Logger GLOBAL_LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    
    private AppLogger(){
        setupGlobalLogger();
    }

    public static AppLogger getInstance() {
        return instance;
    }
    
    public static void load(){
        if (instance == null) {
            synchronized (AppLogger.class) {
                if (instance == null) {
                    instance = new AppLogger();
                }
            }
        }
    }

    public static void setupGlobalLogger() {
        try {
            GLOBAL_LOGGER.addHandler(new StreamHandler(new FileOutputStream(new File(appHome + "/" + "global_logs.log")), new SimpleFormatter()));
            GLOBAL_LOGGER.setLevel(Level.ALL);
        } catch (FileNotFoundException ex) {
            System.out.println(ex.toString());
        }
    }

}
