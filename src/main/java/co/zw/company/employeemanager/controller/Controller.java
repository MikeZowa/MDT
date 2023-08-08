package co.zw.company.employeemanager.controller;

import co.zw.company.employeemanager.dto.EmployeeDto;
import co.zw.company.employeemanager.model.Employee;
import co.zw.company.employeemanager.service.EmployeeService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("api")
public class Controller {

    private final EmployeeService employeeService;

    @RequestMapping(value = "save", method = RequestMethod.POST)
    public ResponseEntity<Employee> saveEmployee(@RequestBody Employee employee){
        return new ResponseEntity<>(employeeService.createNewEmployee(employee), HttpStatus.CREATED);   }

    @RequestMapping(value = "get", method = RequestMethod.GET)
    public ResponseEntity<List<EmployeeDto>> getAllEmployees(){
        return new ResponseEntity<>(employeeService.getAllEmployees(),HttpStatus.OK);
    }
}
