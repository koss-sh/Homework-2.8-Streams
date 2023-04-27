package com.example.homeworkstreams;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import static org.apache.commons.lang3.StringUtils.isAlpha;

@Service
public class EmployeeService {
    private static final int LIMIT = 10;

    private  final List<Employee> employees = new ArrayList<>();

    public Employee addEmployee(String firstName, String lastName, int department, int salary) {
        Employee employee = new Employee(firstName, lastName, department, salary);
        if (employees.contains(employee))
            throw new RuntimeException();
        validateInput(firstName, lastName);
   /*     if(!StringUtils.isAlpha(firstName) || !StringUtils.isAlpha(lastName))
            throw new BadRequestException();*/

        if (employees.size() < LIMIT) {
            employees.add(employee);
            return employee;
        }
        throw new RuntimeException();
    }


    public Employee removeEmployee (String firstName, String lastName) {
        Employee employee = new Employee(firstName, lastName);
        validateInput(firstName, lastName);
        if (!employees.contains(employee))
            throw new RuntimeException();
        employees.remove(employee);
            return employee;
    }
    public Employee findEmployee (String firstName, String lastName) {
        Employee employee = new Employee(firstName, lastName);
        validateInput(firstName, lastName);
        if (!employees.contains(employee))
            throw new RuntimeException();
        return employee;
    }
    public List<Employee> getAll() {
        return new ArrayList<>(employees);
    }
    private void validateInput(String firstName, String lastName) {
        if(!isAlpha(firstName) || !isAlpha(lastName))
            throw new BadRequestException();
    }


}
