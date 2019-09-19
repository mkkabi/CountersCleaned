package model;

public enum CounterType {

   WATER ("/resources/images/water.png"),
   GAS ("/resources/images/gas.png"),
   ELECTRICITY ("resources/images/electricity.png"),
   CUSTOM ("");

   private String icoLocation;
   
   CounterType(String ico) {
       this.icoLocation = ico;
   }

   public String ico() {
       return icoLocation;
   }
}
