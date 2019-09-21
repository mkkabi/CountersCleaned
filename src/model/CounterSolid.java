package model;

import java.time.Instant;
import java.time.temporal.ChronoField;
import java.util.Date;

public class CounterSolid extends AbstractCounter{

    CounterType type;
    
    public CounterSolid(String n) {
        this.name = n;
        this.setSerialNumber();
    }

    
 
}
