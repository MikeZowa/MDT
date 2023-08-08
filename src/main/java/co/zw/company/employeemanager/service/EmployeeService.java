package co.zw.company.employeemanager.service;

import co.zw.company.employeemanager.dto.EmployeeDto;
import co.zw.company.employeemanager.model.Employee;

import java.util.List;

public interface EmployeeService {
    Employee createNewEmployee(Employee employee);

    List<EmployeeDto> getAllEmployees();

    Employee updateEmployee(Long id, Employee updatedEmployee);
}
