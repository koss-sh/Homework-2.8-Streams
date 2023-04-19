package com.example.homeworkstreams;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/departments")
public class DepartmentController {
    private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @GetMapping("/min-salary")
    public Employee findDeptMinSalary(@RequestParam("departmentId") int department) {
        return departmentService.findDeptMinSalary(department);
    }

    @GetMapping("/max-salary")
    public Employee findDeptMaxSalary(@RequestParam("departmentId") int department) {
        return departmentService.findDeptMaxSalary(department);
    }

    @GetMapping(value = "/all", params = "departmentId")
    public List<Employee> getAllEmployeesOfDept(@RequestParam("departmentId") int department) {
        return departmentService.getAllEmployeesOfDept(department);
    }

    @GetMapping("/all")
    public Map<Integer, List<Employee>> getAllEmployees() {
        return departmentService.getAllEmployeesByDept();
    }
}
