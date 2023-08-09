package co.zw.company.employeemanager.controller;

import co.zw.company.employeemanager.dto.EmployeeDto;
import co.zw.company.employeemanager.model.Employee;
import co.zw.company.employeemanager.service.EmployeeService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/")
public class Controller {

    private final EmployeeService employeeService;

    @RequestMapping(value = "save", method = RequestMethod.POST)
    public ResponseEntity<Employee> saveEmployee(@Valid @RequestBody Employee employee, BindingResult result){
        if (result.hasErrors()){
            return new ResponseEntity<>(employee, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(employeeService.createNewEmployee(employee), HttpStatus.CREATED);
    }

    @GetMapping("employees")
    public ResponseEntity<List<EmployeeDto>> getAllEmployees(){
        return new ResponseEntity<>(employeeService.getAllEmployees(),HttpStatus.OK);
    }

    @DeleteMapping(path = "delete/{employeeId}")
    public void delete(@PathVariable("employeeId") Long employeeId){
        employeeService.deleteEmployeeById(employeeId);

    }
}
