
package model;

import java.io.Serializable;
import java.util.Objects;


// Use of the Template pattern


 public abstract class AbstractCounter implements Counter, Serializable{
	protected String name, csvFileName;
	protected double rate, previousData;
        protected CounterType type;

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
		return previousData;
	}

        @Override
	public void setRate(double rate) {
		this.rate = Objects.requireNonNull(rate);
	}
	
        @Override
	public double getRate(){
		return this.rate;
	}

        @Override
	public void setFileName(String f){
		csvFileName = f;
	}
	
        @Override
	public String getFileName(){
		return csvFileName;
	}
	
	@Override
	public String toString() {
		return name;
	}
        
        
        public String getIcon(){
            return type.ico();
        }
}
