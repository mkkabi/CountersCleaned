package application;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.function.Consumer;

public class Serializer<T> {
 
	public void saveObjects(String file, ArrayList<T> objects)  {
		try (ObjectOutputStream saver = new ObjectOutputStream(new FileOutputStream(file))) {
			for (T t : objects) {
				saver.writeObject(t);
				System.out.println("saved " + t.toString());
			}
		} catch (FileNotFoundException e) {
			System.out.println(e.toString());
		}catch(IOException io){System.out.println(io.toString());}
	}

	/**
	 *
	 * @param <T>
	 * @param file location
	 * @param destination of new objects to be written to
	 * @param consumer whatever action may be performed on each single object during restoration
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public <T> void restoreObjects(String file, Consumer consumer) throws FileNotFoundException {
		try(ObjectInputStream loader = new ObjectInputStream(new FileInputStream(file));){
		while (true) {
			T obj = (T) loader.readObject();
			System.out.println("loaded " + obj.toString());
			consumer.accept(obj);
		}
		}catch(IOException | ClassNotFoundException e){System.out.println(e.toString()+" from "+this.getClass());}
	}

}
