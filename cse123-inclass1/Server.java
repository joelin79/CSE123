public class Server extends Employee {
   private double totalTipsCollected;

   public Server(String employeeName) {
       super(employeeName);
       totalTipsCollected = 0.0;
   }

   public void collectTips(double amount) {
      System.out.printf("Ch-ching!! $%.2f\n", amount);
      totalTipsCollected += amount;
      System.out.printf(getEmployeeName() + " has collected $%.2f worth of tips so far!\n", totalTipsCollected);
   }
}
