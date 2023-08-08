package co.zw.company.employeemanager.mapper;

import co.zw.company.employeemanager.dto.EmployeeDto;
import co.zw.company.employeemanager.model.Employee;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class EmployeeMapper implements Function<Employee, EmployeeDto> {


    @Override
    public EmployeeDto apply(Employee employee) {
        return new EmployeeDto(
                employee.getFullName(),
                employee.getPosition());
    }
}
