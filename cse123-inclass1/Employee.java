// This class represents an Employee.
public class Employee {
    private String name;

    public Employee(String name) {
        this.name = name;
    }

    // Returns this employee's name.
    public String getEmployeeName() {
        return name;
    }

    // Returns the number of hours this Employee
    // has to work weekly.
    public int getHours() {
        return 40;
    }

    // Returns this employees hourly pay rate.
    public double getHourlyRate() {
        return 50.0;
     }

    // Returns the number of vacation days this
    // Employee has in a given year.
    public int getVacationDays() {
        return 10;
    }

    // Returns a string representation of this Employee, 
    // specifically "Employee"
    public String toString() {
        return "Employee";
    }
}

