package co.zw.company.employeemanager.controller;

import co.zw.company.employeemanager.model.Employee;
import co.zw.company.employeemanager.repository.EmployeeRepository;
import co.zw.company.employeemanager.service.EmployeeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@WebMvcTest(Controller.class)
@AutoConfigureJsonTesters
public class EmployeeControllerTest {
    @Autowired
    private JacksonTester<Employee> employeeJacksonTester;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private EmployeeService employeeService;

    private Employee employee;

    @Mock
    private EmployeeRepository employeeRepository;

    @Before
    public void setUp(){
        employee = new Employee();
        employee.setId(1L);
        employee.setFullName("Nyatsimba Mutota");
        employee.setPosition("King");
        employee.setUniqueKey(1234);
    }

    @Test
    public void givenEmployee_whenCreateEmployee_shouldReturnCreatedEmployee() throws Exception{

        given(employeeService.createNewEmployee(any(Employee.class))).willReturn(employee);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/save").
                        contentType(MediaType.APPLICATION_JSON_VALUE).
                        content(objectMapper.writeValueAsString(employee)))
                .andExpect(status().isCreated());

    }

    @Test
    public void givenEmployeeWhenSerializeThenReturnEmployeeJson() throws Exception {
        assertThat(employeeJacksonTester.write(employee)).hasJsonPathNumberValue("@.id");
        assertThat(employeeJacksonTester.write(employee)).hasJsonPathStringValue("@.fullName");
        assertThat(employeeJacksonTester.write(employee)).hasJsonPathStringValue("@.position");
        assertThat(employeeJacksonTester.write(employee)).hasJsonPathNumberValue("@.uniqueKey");
        assertThat(employeeJacksonTester.write(employee)).
                extractingJsonPathNumberValue("@.id").isEqualTo(1);
        assertThat(employeeJacksonTester.write(employee)).
                extractingJsonPathStringValue("@.fullName").isEqualTo("Nyatsimba Mutota");
        assertThat(employeeJacksonTester.write(employee)).
                extractingJsonPathStringValue("@.position").isEqualTo("King");
        assertThat(employeeJacksonTester.write(employee)).
                extractingJsonPathNumberValue("@.uniqueKey").isEqualTo(1234);
    }

    @Test
    public void givenEmployeeWhenDeserializeThenReturnEmployeeObject() throws Exception {
        final String content = "{\"id\":\"1\",\"fullName\":\"Nyatsimba Mutota\",\"position\":\"King\",\"uniqueKey\":\"1234\"}";
        assertThat(employeeJacksonTester.parse(content).getObject().toString()).isEqualTo(employee.toString());
        assertThat(employeeJacksonTester.parseObject(content).getId()).isEqualTo(employee.getId());
        assertThat(employeeJacksonTester.parseObject(content).getFullName()).isEqualTo(employee.getFullName());
        assertThat(employeeJacksonTester.parseObject(content).getPosition()).isEqualTo(employee.getPosition());
        assertThat(employeeJacksonTester.parseObject(content).getUniqueKey()).isEqualTo(employee.getUniqueKey());
    }

    @Test
    public void givenEmployeeIdAndUpdatedEmployee_whenUpdateEmployee_thenEmployeeUpdated() throws Exception {
        // Create an updated employee
        Employee updatedEmployee = new Employee();
        updatedEmployee.setId(1L);
        updatedEmployee.setFullName("Rowen Mariri");
        updatedEmployee.setPosition("Engineer");
        updatedEmployee.setUniqueKey(5678);

        // Mock the behavior of the employeeService updateEmployee method
        Mockito.when(employeeService.updateEmployee(Mockito.eq(1L), Mockito.any(Employee.class)))
                .thenReturn(updatedEmployee);

        // Perform the PUT request to update the employee
        mockMvc.perform(MockMvcRequestBuilders.put("/api/update/1")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(updatedEmployee)))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.fullName").value("Rowen Mariri"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.position").value("Engineer"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.uniqueKey").value(5678));

        // Verify that the employeeService updateEmployee method was called with the expected parameters
        Mockito.verify(employeeService, Mockito.times(1))
                .updateEmployee(Mockito.eq(1L), Mockito.any(Employee.class));
    }
}
