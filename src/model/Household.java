package model;

import appUtils.NIO;
import java.io.Serializable;
import java.util.ArrayList;

public class Household<T extends AbstractCounter> implements Serializable {

    public static final String SAVE_FILE = NIO.appHome + "houses.ser";
    public static ArrayList<Household> housholds = new ArrayList();
    private final String name;
    private ArrayList<T> counters;
    private final int ID;
    private int countersNumber;

    public Household(String n) {
        ID = (int) (Math.random() * 10000);
        name = n;
        System.out.println("created Household with name " + n);
        counters = new ArrayList();
        housholds.add(this);
    }
    
    public void increateCountersNumber(){
        countersNumber ++;
    }
    
    public int getCountersNumber(){
        return this.countersNumber;
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
