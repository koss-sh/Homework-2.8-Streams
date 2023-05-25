package com.example.homeworkstreams;


import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/department")
public class DepartmentController {
    private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @GetMapping("/{id}/salary/min")
    public double findDeptMinSalary(@PathVariable int id) {
        return departmentService.findDeptMinSalary(id);
    }

    @GetMapping("/{id}/salary/max")
    public double findDeptMaxSalary(@PathVariable int id) {
        return departmentService.findDeptMaxSalary(id);
    }

    @GetMapping("/{id}/salary/sum")
    public double countTotalDeptSalary(@PathVariable int id) {
        return departmentService.countTotalDeptSalary(id);
    }

    @GetMapping(value = "/{id}/employees")
    public List<Employee> getAllEmployeesOfDept(@PathVariable int id) {
        return departmentService.getAllEmployeesOfDept(id);
    }

    @GetMapping("/all")
    public Map<Integer, List<Employee>> getAllEmployees() {
        return departmentService.getAllEmployeesByDept();
    }
}
