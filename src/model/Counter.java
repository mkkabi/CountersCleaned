package model;

import java.io.Serializable;

public interface Counter extends Serializable {
    
    	 void setName(String name);
	 String getName();
	 void setPreviousData(double previousData);
	 double getPreviousData();
	 void setRate(double rate);
	 double getRate();
	 void setFileName(String f);
	 String getFileName();
	 String toString();
}
