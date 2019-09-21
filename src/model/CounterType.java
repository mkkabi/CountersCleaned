package model;

public enum CounterType {

   WATER ("water","/resources/images/water.png"),
   GAS ("gas","/resources/images/gas.png"),
   ELECTRICITY ("electricity","resources/images/electricity.png"),
   CUSTOM ("custom","resources/images/tachometer.png");

   private String icoLocation;
   private String name;
   
   CounterType(String name, String ico) {
       this.name = name;
       this.icoLocation = ico;
   }

   public String ico() {
       return icoLocation;
   }
}
