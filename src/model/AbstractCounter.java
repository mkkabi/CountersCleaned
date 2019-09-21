package model;

import java.util.Objects;

// Use of the Template pattern
public abstract class AbstractCounter implements Counter {

    protected String name, csvFileName;
    protected double rate, previousData;
    protected CounterType type;
    protected int serialNumber;
    protected Household house;

    
    @Override
    public void setSerialNumber(){
        this.serialNumber = (int)(Math.random()*10000)+name.chars().sum();
    }
    
    @Override
    public void assignToHouse(Household house){
        this.house = house;
    }
    
    @Override
    public int getSerialNumber(){
        return serialNumber;
    }
    
    @Override
    public String getImage() {
        return type.ico();
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public void setPreviousData(double previousData) {
        this.previousData = previousData;
    }

    @Override
    public double getPreviousData() {
        return this.previousData;
    }

    @Override
    public void setRate(double rate) {
        this.rate = Objects.requireNonNull(rate);
    }

    @Override
    public double getRate() {
        return this.rate;
    }

    @Override
    public void setFileName(String f) {
        csvFileName = f;
    }

    @Override
    public String getFileName() {
        return csvFileName;
    }

    @Override
    public String toString() {
        return name+" ID: "+serialNumber;
    }

    public String getIcon() {
        return type.ico();
    }

    public void setType(CounterType type) {
        this.type = type;
    }
    
    @Override
    public int compareTo(Object o) {
        if(o instanceof Counter)
            return getSerialNumber() - ((Counter) o).getSerialNumber();
        return -1;
    }
}
