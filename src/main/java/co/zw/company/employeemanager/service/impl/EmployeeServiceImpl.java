package co.zw.company.employeemanager.service.impl;

import co.zw.company.employeemanager.dto.EmployeeDto;
import co.zw.company.employeemanager.mapper.EmployeeMapper;
import co.zw.company.employeemanager.model.Employee;
import co.zw.company.employeemanager.repository.EmployeeRepository;
import co.zw.company.employeemanager.service.EmployeeService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;

    @Override
    public Employee createNewEmployee(Employee employee){
        return employeeRepository.save(employee);
    }

    @Override
    public List<EmployeeDto> getAllEmployees(){
        return employeeRepository.findAll()
                .stream()
                .map(employeeMapper
                ).collect(Collectors.toList());
    }

    public Employee updateEmployee(Long id, Employee updatedEmployee) {
        Optional<Employee> optionalEmployee = employeeRepository.findById(id);
        if (optionalEmployee.isPresent()) {
            Employee existingEmployee = optionalEmployee.get();
            existingEmployee.setFullName(updatedEmployee.getFullName());
            existingEmployee.setPosition(updatedEmployee.getPosition());
            existingEmployee.setUniqueKey(updatedEmployee.getUniqueKey());
            return employeeRepository.save(existingEmployee);
        } else {
            return null; // Or throw an exception, depending on your requirements
        }
    }
}
