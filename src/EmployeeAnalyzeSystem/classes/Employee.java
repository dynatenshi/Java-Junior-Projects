package EmployeeAnalyzeSystem.classes;

import java.util.Objects;

public class Employee {
    private final int id;
    private final String firstName;
    private final String lastName;
    private final int age;
    private final String department;
    private final double salary;
    private final String position;

    public Employee(int id, String firstName, String lastName, int age, String department, double salary, String position) {
        this.id = validateId(id);
        this.firstName = validateName(firstName, "First name");
        this.lastName = validateName(lastName, "Last name");
        this.age = validateAge(age);
        this.department = validateDepartment(department);
        this.salary = validateSalary(salary);
        this.position = validatePosition(position);
    }

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public int getAge() {
        return age;
    }

    public String getDepartment() {
        return department;
    }

    public double getSalary() {
        return salary;
    }

    public String getPosition() {
        return position;
    }

    private int validateId(int id) {
        if (id < 0) throw new IllegalArgumentException("ID can't be less than 0");

        return id;
    }

    private String validateName(String name, String nameField) {
        if (name == null || name.trim().isEmpty()) throw new IllegalArgumentException(nameField+" can't be empty");
        if (!name.matches("[a-zA-Za-яА-Я- ] +")) throw new IllegalArgumentException(nameField+" contains illegal characters");

        return name.trim();
    }

    private int validateAge(int age) {
        if (age < 14) throw new IllegalArgumentException("Age can't be less than 14");
        if (age > 80) throw new IllegalArgumentException("Age can't be greater than 80");

        return age;
    }

    private String validateDepartment(String department) {
        if (department == null || department.trim().isEmpty()) throw new IllegalArgumentException("Department can't be empty");

        return department;
    }

    private String validatePosition(String position) {
        if (position == null || position.trim().isEmpty()) throw new IllegalArgumentException("Position can't be empty");

        return position;
    }

    private double validateSalary(double salary) {
        if (salary <= 0) throw new IllegalArgumentException("Salary must be greater than 0");
        if (salary > 1_000_000) throw new IllegalArgumentException("Salary can't be greater than 1000000");

        return salary;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Employee employee)) return false;

        return this.getId() == employee.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getFirstName(), getLastName(),
                getAge(), getPosition(), getSalary(), getDepartment());
    }

    @Override
    public String toString() {
        return String.format("Id:%d | F_Name:%s | L_Name:%s | Age:%d | Department:%s | Position:%s | Salary:%.2f%n",
                getId(), getFirstName(), getLastName(), getAge(), getDepartment(), getPosition(), getSalary());
    }
}
