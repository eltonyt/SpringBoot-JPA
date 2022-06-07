package com.luv2code.springboot.cruddemo.rest;

import com.luv2code.springboot.cruddemo.entity.Employee;
import com.luv2code.springboot.cruddemo.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class EmployeeRestController {

    private EmployeeService employeeService;

    @Autowired
    public EmployeeRestController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/employees")
    public List<Employee> findAll(){
        return employeeService.findAll();
    }

    @GetMapping("/employees/{employeeId}")
    public ResponseEntity getEmployee(@PathVariable Long employeeId){
        Employee employee= employeeService.findById(employeeId);
        if (employee == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Employee not exist");
        return ResponseEntity.status(HttpStatus.OK).body(employee);
    }

    @PostMapping("/employees")
    public ResponseEntity addEmployee(@RequestBody Employee theEmployee){
        theEmployee.setId(0L);
        employeeService.save(theEmployee);
        return ResponseEntity.status(HttpStatus.CREATED).body(theEmployee);
    }

    @PutMapping("/employees")
    public ResponseEntity updateEmployee(@RequestBody Employee theEmployee){
        employeeService.save(theEmployee);
        return ResponseEntity.status(HttpStatus.OK).body(theEmployee);
    }

    @DeleteMapping("employees/{employeeId}")
    public ResponseEntity deleteEmployee(@PathVariable Long employeeId){
        Employee employee = employeeService.findById(employeeId);
        if (employee == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Employee doesn't exist.");
        employeeService.deleteById(employeeId);
        return ResponseEntity.status(HttpStatus.OK).body("Success");
    }
}
