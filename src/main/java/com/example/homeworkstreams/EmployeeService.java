package com.example.homeworkstreams;

import org.apache.commons.lang3.StringUtils;
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
        if(!StringUtils.isAlphanumericSpace(firstName) || !StringUtils.isAlphanumericSpace(lastName))
            throw new BadRequestException();
        for (int i = 0; i < firstName.length(); i++) {
            if (Character.isDigit(firstName.charAt(i)))
                throw new BadRequestException();
        }
        for (int i = 0; i < lastName.length(); i++) {
            if (Character.isDigit(lastName.charAt(i)))
                throw new BadRequestException();
        }
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
