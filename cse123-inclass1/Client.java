import java.util.*;

public class Client {
   public static void main(String[] args) {
      Employee standardEmployee = new Employee("Frankie Blaze");
      System.out.println("Employee:");
      System.out.println("   Works " + standardEmployee.getHours() + " per week");
      System.out.printf("   Earns $%.2f per hour\n", standardEmployee.getHourlyRate());
      System.out.println("   Gets " + standardEmployee.getVacationDays() + " vacation days per year");
      System.out.println();
      
      Server leadServer = new Server("Sam Taylor");
      System.out.println("Server:");
      System.out.println("   Works " + leadServer.getHours() + " per week");
      System.out.printf("   Earns $%.2f leadServer hour\n", leadServer.getHourlyRate());
      System.out.println("   Gets " + leadServer.getVacationDays() + " vacation days per year");
      System.out.print("   ");
      leadServer.collectTips(3.0);
      System.out.println();

      Chef sousChef = new Chef("Gordon Ramsay");
      System.out.println("Chef:");
      System.out.println("   Works " + sousChef.getHours() + " per week");
      System.out.printf("   Earns $%.2f per hour\n", sousChef.getHourlyRate());
      System.out.println("   Gets " + sousChef.getVacationDays() + " vacation days per year");
      sousChef.cookFood("Beef Wellington");
      System.out.println();

      Chef headChef = new Chef("Julia Child");
      System.out.println("Chef:");
      System.out.println("   Works " + headChef.getHours() + " per week");
      System.out.printf("   Earns $%.2f per hour\n", headChef.getHourlyRate());
      System.out.println("   Gets " + headChef.getVacationDays() + " vacation days per year");
      headChef.cookFood("French Onion Soup");
      headChef.cookFood("Crepe");
      System.out.println();
   }
}
