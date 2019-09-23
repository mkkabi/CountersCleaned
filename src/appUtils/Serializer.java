package appUtils;

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

public class Serializer {

    private static volatile Serializer instance = null;

    private Serializer() {
    }

    public static Serializer getInstance() {
        if (instance == null) {
            synchronized (NIO.class) {
                if (instance == null) {
                    instance = new Serializer();
                }
            }
        }
        return instance;
    }

    public static <T> void saveObjects(String file, ArrayList<T> objects) {
        try (ObjectOutputStream saver = new ObjectOutputStream(new FileOutputStream(file))) {
            for (T t : objects) {
                saver.writeObject(t);
            }
        } catch (FileNotFoundException e) {
            Logger.getGlobal().log(Level.INFO, e.toString());
        } catch (IOException io) {
            Logger.getGlobal().log(Level.INFO, io.toString());
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
    public static <T> void restoreObjects(String file, Consumer consumer) {

        try (ObjectInputStream loader = new ObjectInputStream(new FileInputStream(file));) {
            while (true) {
                consumer.accept(loader.readObject());
            }
        } catch (FileNotFoundException f) {
            Logger.getGlobal().log(Level.INFO, f.toString());
        } catch (IOException | ClassNotFoundException e) {
            Logger.getGlobal().log(Level.INFO, e.toString());

        }
    }

}
