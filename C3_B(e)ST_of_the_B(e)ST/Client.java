// Joe Lin
// 03/13/2025
// CSE 123
// C3: B(e)ST of the B(e)ST
// TA: Benoit Le

import java.util.*;
import java.io.*;

public class Client {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner console = new Scanner(System.in);
        System.out.println("Welcome to the CSE 123 Collection Manager! " +
                           "To begin, enter your desired mode of operation:");
        System.out.println();
        System.out.println("1) Start with an empty collection manager");
        System.out.println("2) Load collection from file");
        System.out.print("Enter your choice here: ");

        int choice = Integer.parseInt(console.nextLine());
        while (choice != 1 && choice != 2) {
            System.out.println("Invalid choice! Try again");
            choice = Integer.parseInt(console.nextLine());
        }

        CollectionManager collectionManager = null;
        if (choice == 1) {
            collectionManager = new CollectionManager();
        } else if (choice == 2) {
            System.out.print("Enter file to read: ");
            String inFileName = console.nextLine();
            File inFile = new File(inFileName);
            while (!inFile.exists()) {
                System.out.println("  File does not exist. Please try again.");
                System.out.print("Enter file to read: ");
                inFileName = console.nextLine();
                inFile = new File(inFileName);
            }
    
            collectionManager = new CollectionManager(new Scanner(inFile));
            System.out.println("Collection manager created!");
            System.out.println();
        }

        menu(console);
        String option = console.nextLine();
        while (!option.equalsIgnoreCase("quit")) {
            System.out.println();

            if (option.equalsIgnoreCase("add")) {
                // TODO: Call your collection manager's add method and 
                // pass in the result of the Item's parse() method
                collectionManager.add(EarthquakeReport.parse(console));
                System.out.println();
            } else if (option.equalsIgnoreCase("contains")) {
                // TODO: Print the result of your collection manager's 
                // contains method and pass in the result of the Item's parse() method
                System.out.println("Collection contains earthquake: " + collectionManager.contains(EarthquakeReport.parse(console)));
                System.out.println();
            } else if (option.equalsIgnoreCase("print")) {
                System.out.println(collectionManager.toString());
                System.out.println();
            } else if (option.equalsIgnoreCase("creative")) {
                System.out.print("Enter minimum magnitude to filter (e.g., 6.0): ");
                double minMagnitude = Double.parseDouble(console.nextLine());

                System.out.print("Enter maximum magnitude to filter (e.g., 8.0): ");
                double maxMagnitude = Double.parseDouble(console.nextLine());

                List<EarthquakeReport> filtered = collectionManager.filter(minMagnitude, maxMagnitude);
                System.out.println("Earthquakes with magnitude between " + minMagnitude + " and " + maxMagnitude + ":");
                for (EarthquakeReport report : filtered) {
                    System.out.println("- " + report);
                }                System.out.println();
            } else if (option.equalsIgnoreCase("save")) {
                System.out.print("Enter file to save to: ");
                String outFileName = console.nextLine();
                PrintStream outFile = new PrintStream(new File(outFileName));
                collectionManager.save(outFile);
                System.out.println("Collection Manager exported!");
                System.out.println();
            } else if (!option.equalsIgnoreCase("quit")) {
                System.out.println("  Invalid choice. Please try again.");
                System.out.println();
            }

            menu(console);
            option = console.nextLine();
        }
    }

    private static void menu(Scanner console) {
        System.out.println("What would you like to do? Choose an option in brackets.");
        System.out.println("  [add] item");
        System.out.println("  [contains] item");
        System.out.println("  [print] my collection");
        System.out.println("  [save] my collection");
        System.out.println("  [creative] extension");
        System.out.println("  [quit] program");
    }
}
