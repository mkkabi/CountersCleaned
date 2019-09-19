package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Set;

public class Household <T extends AbstractCounter> implements Serializable{

	public static final String SAVE_FILE = "houses.ser";
	public static ArrayList<Household> housholds = new ArrayList();
	private final String name;
	private ArrayList<T> counters;
	private final int ID;

	public Household(String n) {
		ID = (int) (Math.random() * 10000);
		name = n;
		System.out.println("created Household with name " + n);
		counters = new ArrayList();
		housholds.add(this);
	}

	public String getName() {
		return this.name;
	}
	
	@Override
	public String toString() {
		return this.name + " serial ID = " + ID;
	}

	public void addCounter(T t) {
		counters.add(t);
	}

	public ArrayList<T> getCounters() {
		return this.counters;
	}

}
