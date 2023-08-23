package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.exception.EmployeeCreateException;
import com.thoughtworks.springbootemployee.exception.EmployeeInactiveException;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

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
        Employee employee = new Employee(null, "Lucy", 20, "Female", 3000, 1L);
        Employee savedEmployee = new Employee(1L, "Lucy", 20, "Female", 3000, 1L);
        when(mockedEmployeeRepository.addEmployee(employee)).thenReturn(savedEmployee);
        //when
        Employee employeeResponse = employeeService.createEmployee(employee);
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
        Employee employee = new Employee(null, "Lucy", 17, "Female", 3000, 1L);
        //when// then
        EmployeeCreateException employeeCreateException = assertThrows(EmployeeCreateException.class, () -> {
            employeeService.createEmployee(employee);
        });
        assertEquals("Employee must be 18-65 years old", employeeCreateException.getMessage());
    }

    @Test
    void should_throw_exception_when_create_given_employee_service_and_employee_whose_age_is_greater_than_65() {
        //given
        Employee employee = new Employee(null, "Lucy", 66, "Female", 3000, 1L);
        //when// then
        EmployeeCreateException employeeCreateException = assertThrows(EmployeeCreateException.class, () -> {
            employeeService.createEmployee(employee);
        });
        assertEquals("Employee must be 18-65 years old", employeeCreateException.getMessage());
    }

    @Test
    void should_return_employee_with_active_status_when_create_given_employee_service_and_employee_with_valid_age() {
        Employee employee = new Employee(null, "Lucy", 20, "Female", 3000, 1L);
        Employee savedEmployee = new Employee(1L, "Lucy", 20, "Female", 3000, 1L);
        savedEmployee.setEmployeeActive(Boolean.TRUE);
        when(mockedEmployeeRepository.addEmployee(employee)).thenReturn(savedEmployee);
        //when
        Employee employeeResponse = employeeService.createEmployee(employee);
        //then
        assertTrue(employeeResponse.isEmployeeActive());
        assertEquals(savedEmployee.getId(), employeeResponse.getId());
        assertEquals("Lucy", employeeResponse.getName());
        assertEquals(20, employeeResponse.getAge());
        assertEquals("Female", employeeResponse.getGender());
        assertEquals(3000, employeeResponse.getSalary());
        assertEquals(1, employeeResponse.getCompanyId());

    }

    @Test
    void should_return_employee_with_active_status_false_when_delete_given_employee_service_and_employee_id() {
        Employee employee = new Employee(1L, "Lucy", 20, "Female", 3000, 1L);
        employee.setEmployeeActive(Boolean.TRUE);
        when(mockedEmployeeRepository.findByEmployeeId(employee.getId())).thenReturn(employee);
        //when
        employeeService.deleteEmployee(employee.getId());
        //then
        verify(mockedEmployeeRepository).updateEmployee(eq(employee.getId()), argThat(tempEmployee -> {
            assertFalse(tempEmployee.isEmployeeActive());
            assertEquals("Lucy", tempEmployee.getName());
            assertEquals(20, tempEmployee.getAge());
            assertEquals("Female", tempEmployee.getGender());
            assertEquals(3000, tempEmployee.getSalary());
            assertEquals(1L, tempEmployee.getCompanyId());
            return true;
        }));
    }

    @Test
    void should_return_updated_employee_when_update_given_employee_service_and_employee_and_is_active_true() {
        Employee employee = new Employee(1L, "Lucy", 20, "Female", 3000, 1L);
        employee.setEmployeeActive(Boolean.TRUE);
        Employee newEmployee = new Employee(1L,"Lucy",21,"Female",4000,1L);
        newEmployee.setEmployeeActive(Boolean.TRUE);
        when(mockedEmployeeRepository.findByEmployeeId(employee.getId())).thenReturn(employee);
        //when
        employeeService.updateEmployee(employee.getId(), newEmployee);
        //then
        verify(mockedEmployeeRepository).updateEmployee(eq(employee.getId()), argThat(tempEmployee -> {
            assertTrue(tempEmployee.isEmployeeActive());
            assertEquals("Lucy", tempEmployee.getName());
            assertEquals(21, tempEmployee.getAge());
            assertEquals("Female", tempEmployee.getGender());
            assertEquals(4000, tempEmployee.getSalary());
            assertEquals(1L, tempEmployee.getCompanyId());
            return true;
        }));
    }

    @Test
    void should_return_EmployeeInactiveException_when_update_given_employee_service_and_employee_and_is_active_false() {
        Employee employee = new Employee(1L, "Lucy", 20, "Female", 3000, 1L);
        employee.setEmployeeActive(Boolean.FALSE);
        //when
        when(mockedEmployeeRepository.findByEmployeeId(employee.getId())).thenReturn(employee);
        //then
        EmployeeInactiveException employeeInactiveException = assertThrows(EmployeeInactiveException.class, () ->
            employeeService.updateEmployee(employee.getId(), employee)
        );
        assertEquals("Employee is inactive", employeeInactiveException.getMessage());
    }
}
