public class Chef extends Employee {
   private int ordersFinished;

   public Chef(String employeeName) {
       super(employeeName);
       ordersFinished = 0;
   }

   public void cookFood(String order) {
      System.out.println(order + " order up!");
      ordersFinished++;
      System.out.println(getEmployeeName() + " has finished " + ordersFinished + " orders so far!");
   }
   
   @Override
   public double getHourlyRate() {
       return super.getHourlyRate() * 10;
   }
}
