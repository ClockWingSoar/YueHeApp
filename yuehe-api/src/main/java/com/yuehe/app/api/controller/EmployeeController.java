package com.yuehe.app.api.controller;

import com.yuehe.app.entity.Employee;
import com.yuehe.app.service.EmployeeService;
import com.yuehe.app.util.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/employees")
@CrossOrigin(origins = "*")
public class EmployeeController {
    
    @Autowired
    private EmployeeService employeeService;
    
    @GetMapping
    public BaseResponse<Page<Employee>> getAllEmployees(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {
        try {
            Page<Employee> employees = employeeService.getAllEmployees(page, size, sortBy, sortDir);
            return BaseResponse.success(employees);
        } catch (Exception e) {
            return BaseResponse.error("获取员工列表失败: " + e.getMessage());
        }
    }
    
    @GetMapping("/{id}")
    public BaseResponse<Employee> getEmployeeById(@PathVariable String id) {
        try {
            Employee employee = employeeService.getEmployeeById(id);
            if (employee != null) {
                return BaseResponse.success(employee);
            } else {
                return BaseResponse.error("员工不存在");
            }
        } catch (Exception e) {
            return BaseResponse.error("获取员工信息失败: " + e.getMessage());
        }
    }
    
    @PostMapping
    public BaseResponse<Employee> createEmployee(@RequestBody Employee employee) {
        try {
            Employee createdEmployee = employeeService.createEmployee(employee);
            return BaseResponse.success(createdEmployee);
        } catch (Exception e) {
            return BaseResponse.error("创建员工失败: " + e.getMessage());
        }
    }
    
    @PutMapping("/{id}")
    public BaseResponse<Employee> updateEmployee(@PathVariable String id, @RequestBody Employee employee) {
        try {
            Employee updatedEmployee = employeeService.updateEmployee(id, employee);
            return BaseResponse.success(updatedEmployee);
        } catch (Exception e) {
            return BaseResponse.error("更新员工失败: " + e.getMessage());
        }
    }
    
    @DeleteMapping("/{id}")
    public BaseResponse<Void> deleteEmployee(@PathVariable String id) {
        try {
            employeeService.deleteEmployee(id);
            return BaseResponse.success(null);
        } catch (Exception e) {
            return BaseResponse.error("删除员工失败: " + e.getMessage());
        }
    }
}
