package EmployeeAnalyzeSystem.classes;

import java.util.*;
import java.util.stream.Collectors;

public class EmployeeAnalytics {
    private final List<Employee> employees;

    public EmployeeAnalytics(List<Employee> employees) {
        this.employees = new ArrayList<>(employees);
    }

    public Employee[] getEmployees() {
        return employees.toArray(new Employee[0]);
    }

    public Employee[] getEmployeesOlderThan(int age) {
        return employees.stream()
                .filter(e -> e.getAge() > age)
                .toArray(Employee[]::new);
    }

    public Employee[] getEmployeesByDepartment(String department) {
        return employees.stream()
                .filter(e -> e.getDepartment().equals(department))
                .toArray(Employee[]::new);
    }

    public Employee[] getEmployeesByPosition(String position) {
        return employees.stream()
                .filter(e -> e.getPosition().equals(position))
                .toArray(Employee[]::new);
    }

    public double getAverageSalary() {
        return employees.stream()
                .mapToDouble(Employee::getSalary)
                .sum() / employees.size();
    }

    public double getAverageSalaryByDepartment(String department) {
        return employees.stream()
                .filter(e -> e.getDepartment().equals(department))
                .mapToDouble(Employee::getSalary)
                .average()
                .orElse(0);
    }

    public double getTotalSalary() {
        return employees.stream()
                .mapToDouble(Employee::getSalary)
                .sum();
    }

    public Map<String, List<Employee>> groupByDepartment() {
        return employees.stream()
                .collect(Collectors.groupingBy(Employee::getDepartment));
    }

    public Map<String, List<Employee>> groupByPosition() {
        return employees.stream()
                .collect(Collectors.groupingBy(Employee::getPosition));
    }

    public Map<String, Double> getAverageSalaryByDepartments() {
        return employees.stream()
                .collect(Collectors.groupingBy(
                        Employee::getDepartment,
                        Collectors.averagingDouble(Employee::getSalary)
                ));
    }

    public Employee[] getTopNEarners(int n) {
        return employees.stream()
                .sorted(Comparator.comparingDouble(Employee::getSalary)
                        .reversed())
                        .limit(n)
                        .toArray(Employee[]::new);
    }

    public Employee[] getEmployeesSortedByAge() {
        return employees.stream()
                .sorted(Comparator.comparing(Employee::getAge))
                .toArray(Employee[]::new);
    }

    public Employee[] getEmployeesSortedBySalaryDesc() {
        return employees.stream().
                sorted(Comparator.comparingDouble(Employee::getSalary)
                        .reversed())
                        .toArray(Employee[]::new);
    }

    public String getSalaryRangeByDepartment(String department) {
        DoubleSummaryStatistics summary = employees.stream()
                .filter(e -> e.getDepartment().equals(department))
                .collect(Collectors.summarizingDouble(Employee::getSalary));

        return String.format("Min:%.2f | Max:%.2f | Avg:%.2f%n",
                summary.getMin(), summary.getMax(), summary.getAverage());
    }

    public Map<String, Long> getAgeDistribution() {
        return employees.stream()
                .collect(Collectors.groupingBy(e -> {
            int age = e.getAge();

            if (age < 25) {
                return "Under 25";
            } else if (age <= 40) {
                return "Between 25 - 40";
            } else {
                return "Older than 40";
            }
        }, Collectors.counting()));
    }

    public Map<String, Long> findCommonPositionsInDepartment(String department) {
        return employees.stream()
                .filter(e -> e.getDepartment().equals(department))
                .collect(Collectors.groupingBy(Employee::getPosition, Collectors.counting()));
    }

    @Override
    public int hashCode() {
        return Objects.hash(employees);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof EmployeeAnalytics emp)) return false;

        return this.employees.equals(emp.employees);
    }

    @Override
    public String toString() {
        return employees.stream()
                .map(Employee::toString)
                .collect(Collectors.joining("\n"));
    }
}
