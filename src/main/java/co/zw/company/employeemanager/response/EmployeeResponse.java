package co.zw.company.employeemanager.response;

import co.zw.company.employeemanager.dto.EmployeeDto;
import co.zw.company.employeemanager.model.Employee;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmployeeResponse {
    private List<EmployeeDto> content;
    private int pageSize;
    private int pageNumber;
    private int totalElements;
    private int totalPages;

}
