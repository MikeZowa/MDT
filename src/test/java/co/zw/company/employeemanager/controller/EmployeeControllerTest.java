package co.zw.company.employeemanager.controller;

import co.zw.company.employeemanager.dto.EmployeeDto;
import co.zw.company.employeemanager.model.Employee;
import co.zw.company.employeemanager.response.EmployeeResponse;
import co.zw.company.employeemanager.service.EmployeeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.util.Arrays;
import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@WebMvcTest(Controller.class)
@AutoConfigureJsonTesters
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class EmployeeControllerTest {
    @Autowired
    private JacksonTester<Employee> employeeJacksonTester;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private EmployeeService employeeService;

    private EmployeeDto employeeDto;

    private Employee employee;

    @Before
    public void setUp(){
        employee = new Employee();
        employee.setId(1L);
        employee.setFullName("Nyatsimba Mutota");
        employee.setPosition("King");
        employee.setUniqueKey(1234);

        employeeDto = employeeDto.builder().fullname("munya").position("developer").build();
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
    public void EmployeeController_GetAllEmployees_ReturnEmployeeDto() throws Exception{
        EmployeeDto employeeDto1=EmployeeDto.builder().position("developer").fullname("munya").build();
        when(employeeService.getAllEmployees()).thenReturn(Collections.singletonList((employeeDto1)));
        ResultActions response = mockMvc.perform(get("/api/employees")
                .contentType(MediaType.APPLICATION_JSON));
        response.andExpect(MockMvcResultMatchers.status().isOk());

    }
}
