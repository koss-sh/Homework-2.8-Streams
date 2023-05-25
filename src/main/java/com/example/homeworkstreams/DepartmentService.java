package com.example.homeworkstreams;

import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class DepartmentService {
    private final EmployeeService employeeService;

    public DepartmentService(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }



    public double findDeptMinSalary(int department) {
        return employeeService.getAll().stream()
                .filter(employee -> employee.getDepartment() == department)
                .mapToDouble(Employee::getSalary)
                .min()
                .orElseThrow(RuntimeException::new);
    }
    public double findDeptMaxSalary(int department) {
        return employeeService.getAll().stream()
                .filter(employee -> employee.getDepartment() == department)
                .mapToDouble(Employee::getSalary)
                .max()
                .orElseThrow(RuntimeException::new);
    }
    public double countTotalDeptSalary(int department) {
        return employeeService.getAll().stream()
                .filter(employee -> employee.getDepartment() == department)
                .mapToDouble(Employee::getSalary)
                .sum();
    }
    public List<Employee> getAllEmployeesOfDept(int department) {
        return employeeService.getAll().stream()
                .filter(employee -> employee.getDepartment() == department)
                .toList();
    }
    public Map<Integer, List<Employee>> getAllEmployeesByDept() {
        return employeeService.getAll().stream()
                .collect(Collectors.groupingBy(Employee::getDepartment));
    }
}
