package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.exception.EmployeeCreateException;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class EmployeeServiceTest {
    private EmployeeService employeeService;
    private EmployeeRepository mockedEmployeeRepository;

    @BeforeEach
    void setUp() {
        mockedEmployeeRepository = mock(EmployeeRepository.class);
        employeeService = new EmployeeService(mockedEmployeeRepository);
    }

    @Test
    void should_return_created_employee_when_create_given_employee_service_and_employee_with_valid_age() {
        //given
        Employee employee = new Employee(null, "Lucy", 20, "Female", 3000, 1L,false);
        Employee savedEmployee = new Employee(1L, "Lucy", 20, "Female", 3000, 1L,true);
        when(mockedEmployeeRepository.addEmployee(employee)).thenReturn(savedEmployee);
        //when
        Employee employeeResponse = employeeService.create(employee);
        //then
        assertEquals(savedEmployee.getId(), employeeResponse.getId());
        assertEquals("Lucy", employeeResponse.getName());
        assertEquals(20, employeeResponse.getAge());
        assertEquals("Female", employeeResponse.getGender());
        assertEquals(3000, employeeResponse.getSalary());
        assertEquals(1, employeeResponse.getCompanyId());
    }

    @Test
    void should_throw_exception_when_create_given_employee_service_and_employee_whose_age_is_less_than_18() {
        //given
        Employee employee = new Employee(null, "Lucy", 17, "Female", 3000, 1L,false);
        //when// then
        EmployeeCreateException employeeCreateException = assertThrows(EmployeeCreateException.class, () -> {
            employeeService.create(employee);
        });
        assertEquals("Employee must be 18-65 years old", employeeCreateException.getMessage());
    }

    @Test
    void should_throw_exception_when_create_given_employee_service_and_employee_whose_age_is_greater_than_65() {
        //given
        Employee employee = new Employee(null, "Lucy", 66, "Female", 3000, 1L,false);
        //when// then
        EmployeeCreateException employeeCreateException = assertThrows(EmployeeCreateException.class, () -> {
            employeeService.create(employee);
        });
        assertEquals("Employee must be 18-65 years old", employeeCreateException.getMessage());
    }

    @Test
    void should_return_employee_with_active_status_when_create_given_employee_service_and_employee_with_valid_age() {
        Employee employee = new Employee(null, "Lucy", 20, "Female", 3000, 1L,false);
        Employee savedEmployee = new Employee(1L, "Lucy", 20, "Female", 3000, 1L,true);
        when(mockedEmployeeRepository.addEmployee(employee)).thenReturn(savedEmployee);
        //when
        Employee employeeResponse = employeeService.create(employee);
        //then
        assertEquals(savedEmployee.getId(), employeeResponse.getId());
        assertEquals("Lucy", employeeResponse.getName());
        assertEquals(20, employeeResponse.getAge());
        assertEquals("Female", employeeResponse.getGender());
        assertEquals(3000, employeeResponse.getSalary());
        assertEquals(1, employeeResponse.getCompanyId());
        assertTrue(employeeResponse.getEmployeeStatus());
    }
}
