package model;

import java.io.Serializable;

public interface Counter extends Serializable, Comparable {
	 String getName();
	 String getFileName();
	 String toString();
         String getImage();
         String getSerialNumber();
         
	 double getPreviousData();
	 double getRate();
	 
         void setType(CounterType type);
         void setPreviousData(double previousData);
         void setName(String name);
         void setRate(double rate);
         void setFileName(String f);
         void setSerialNumber(String ID);
         void assignToHouse(Household house);
         
         
         
}
