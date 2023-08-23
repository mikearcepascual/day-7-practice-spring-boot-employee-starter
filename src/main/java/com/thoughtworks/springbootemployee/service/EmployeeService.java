package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.exception.EmployeeCreateException;
import com.thoughtworks.springbootemployee.exception.EmployeeInactiveException;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    public EmployeeService(EmployeeRepository mockedEmployeeRepository) {
        this.employeeRepository = mockedEmployeeRepository;
    }

    public Employee createEmployee(Employee employee) {
       if(employee.hasInvalidAge()){
           throw new EmployeeCreateException();
       }
        return employeeRepository.addEmployee(employee);
    }

    public void deleteEmployee(Long id) {
        Employee matchedEmployee = employeeRepository.findByEmployeeId(id);
        matchedEmployee.setActive(Boolean.FALSE);

        employeeRepository.updateEmployee(id,matchedEmployee);
    }

    public void updateEmployee(Long id, Employee newEmployee) {
        if(!newEmployee.isEmployeeActive()){
            throw new EmployeeInactiveException();
        }
        employeeRepository.updateEmployee(id,newEmployee);
    }
    public List<Employee> listAllEmployees() {
        return employeeRepository.listAllEmployees();
    }
    public Employee findByEmployeeId(Long employeeId) {
        return employeeRepository.findByEmployeeId(employeeId);
    }
    public List<Employee> findEmployeesByGender(String gender) {
        return employeeRepository.findEmployeesByGender(gender);
    }
    public List<Employee> findEmployeesByCompanyId(Long companyId) {
        return employeeRepository.findEmployeesByCompanyId(companyId);
    }
    public List<Employee> listEmployeesByPage(Long pageNumber, Long pageSize) {
        return employeeRepository.listEmployeesByPage(pageNumber,pageSize);
    }
}
