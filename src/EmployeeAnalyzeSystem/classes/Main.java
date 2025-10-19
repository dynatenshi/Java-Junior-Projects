package EmployeeAnalyzeSystem.classes;

import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Employee> testEmployees = List.of(
                new Employee(1, "John", "Doe", 25, "IT", 50000, "Junior"),
                new Employee(2, "Jane", "Smith", 30, "IT", 80000, "Middle"),
                new Employee(3, "Bob", "Johnson", 35, "HR", 60000, "Middle"),
                new Employee(4, "Alice", "Brown", 28, "Finance", 55000, "Junior"),
                new Employee(5, "Charlie", "Wilson", 45, "IT", 120000, "Senior"),
                new Employee(6, "Diana", "Lee", 32, "Finance", 75000, "Middle"),
                new Employee(7, "Eve", "Davis", 29, "HR", 48000, "Junior"),
                new Employee(8, "Frank", "Miller", 50, "Management", 150000, "Lead"),
                new Employee(9, "Grace", "Taylor", 27, "IT", 70000, "Middle"),
                new Employee(10, "Henry", "Anderson", 38, "Finance", 90000, "Senior"),
                new Employee(11, "Johnathan", "Anderson", 20, "Finance", 90000, "Senior")
        );

        EmployeeAnalytics analytics = new EmployeeAnalytics(testEmployees);
        System.out.println(analytics.groupByDepartment());
        System.out.println(analytics.findCommonPositionsInDepartment("IT"));
        System.out.println(analytics.getAgeDistribution());
        System.out.println("Avg salary: "+analytics.getAverageSalary());
        System.out.println(analytics.getAverageSalaryByDepartments());
        System.out.println(analytics.getAverageSalaryByDepartment("HR"));
        System.out.println(Arrays.toString(analytics.getTopNEarners(5)));
        System.out.println(Arrays.toString(analytics.getEmployeesOlderThan(21)));
        System.out.println(analytics.getSalaryRangeByDepartment("Finance"));
        System.out.println(Arrays.toString(analytics.getEmployeesSortedBySalaryDesc()));
    }
}
