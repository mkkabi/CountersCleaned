package model;

import java.time.Instant;
import java.time.temporal.ChronoField;
import java.util.Date;

public class CounterSolid extends AbstractCounter {

    CounterType type;
    Household house;

    public CounterSolid(String n, Household h) {
        this.name = n;
        this.house = h;
        house.increateCountersNumber();
        this.serialNumber = house.getCountersNumber()+"";

    }

}
