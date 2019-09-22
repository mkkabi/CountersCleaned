package controller;

import static controller.NIO.appHome;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.util.logging.StreamHandler;

public class Serializer<T> {

    public void saveObjects(String file, ArrayList<T> objects) {
        try (ObjectOutputStream saver = new ObjectOutputStream(new FileOutputStream(file))) {
            for (T t : objects) {
                saver.writeObject(t);
                
                AppLogger.getInstance().getLogger().info("saved " + t.toString());
                System.out.println("saved " + t.toString());
            }
        } catch (FileNotFoundException e) {
            AppLogger.getInstance().getLogger().info(e.toString());
            System.out.println(e.toString());
        } catch (IOException io) {
            AppLogger.getInstance().getLogger().info(io.toString());
            System.out.println(io.toString());
        }
    }

    /**
     *
     * @param <T>
     * @param file location
     * @param destination of new objects to be written to
     * @param consumer whatever action may be performed on each single object
     * during restoration
     * @throws FileNotFoundException
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public <T> void restoreObjects(String file, Consumer consumer) throws FileNotFoundException {
        Logger.getLogger(Serializer.class.getName()).addHandler(new StreamHandler(new FileOutputStream(new File(appHome + "/" + "logs2.log")), new SimpleFormatter()));
                
        try (ObjectInputStream loader = new ObjectInputStream(new FileInputStream(file));) {
            while (true) {
                T obj = (T) loader.readObject();
//                AppLogger.getInstance().getLogger().info("loaded " + obj.toString());
                Logger.getLogger(Serializer.class.getName()).log(Level.SEVERE, null, "adsf");
                System.out.println("loaded " + obj.toString());
                consumer.accept(obj);
            }
        } catch (IOException | ClassNotFoundException e) {
            AppLogger.getInstance().getLogger().info(e.toString());
            System.out.println(e.toString() + " from " + this.getClass());
        }
    }

}
