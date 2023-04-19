package com.example.homeworkstreams;

import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class EmployeeService {
    private static final int LIMIT = 10;

    private  final List<Employee> employees = new ArrayList<>();

    public Employee addEmployee(String firstName, String lastName, int department, int salary) {
        Employee employee = new Employee(firstName, lastName, department, salary);
        if (employees.contains(employee))
            throw new RuntimeException();
        if (employees.size() < LIMIT) {
            employees.add(employee);
            return employee;
        }
        throw new RuntimeException();
    }


    public Employee removeEmployee (String firstName, String lastName) {
        Employee employee = new Employee(firstName, lastName);
        if (!employees.contains(employee))
            throw new RuntimeException();
        employees.remove(employee);
            return employee;
    }
    public Employee findEmployee (String firstName, String lastName) {
        Employee employee = new Employee(firstName, lastName);
        if (!employees.contains(employee))
            throw new RuntimeException();
        return employee;
    }
    public List<Employee> getAll() {
        return new ArrayList<>(employees);
    }


}
