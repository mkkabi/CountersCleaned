package controller;

import static controller.NIO.appHome;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.util.logging.StreamHandler;

public class AppLogger {

    private static volatile AppLogger instance = null;

    private AppLogger() {
        setUpLogger();
    }
    
    public static AppLogger getInstance() {
        if (instance == null) {
            synchronized (AppLogger.class) {
                if (instance == null) {
                    instance = new AppLogger();
                }
            }
        }
        return instance;
    }

    private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    public static void setUpLogger() {
        try {
            LOGGER.addHandler(new StreamHandler(new FileOutputStream(new File(appHome + "/" + "logs.log")), new SimpleFormatter()));
            LOGGER.setLevel(Level.ALL);
        } catch (FileNotFoundException ex) {
            System.out.println(ex.toString());
        }
    }

    public static Logger getLogger() {
        return LOGGER;
    }
}
