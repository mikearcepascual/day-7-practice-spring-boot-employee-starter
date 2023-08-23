package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.exception.EmployeeCreateException;
import com.thoughtworks.springbootemployee.exception.EmployeeInactiveException;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;

public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    public EmployeeService(EmployeeRepository mockedEmployeeRepository) {
        this.employeeRepository = mockedEmployeeRepository;
    }

    public Employee create(Employee employee) {
       if(employee.hasInvalidAge()){
           throw new EmployeeCreateException();
       }
        return employeeRepository.addEmployee(employee);
    }


    public void delete(Long id) {
        Employee matchedEmployee = employeeRepository.findByEmployeeId(id);
        matchedEmployee.setActive(Boolean.FALSE);

        employeeRepository.updateEmployee(id,matchedEmployee);
    }

    public void update(Long id, Employee newEmployee) {
        if(!newEmployee.isEmployeeActive()){
            throw new EmployeeInactiveException();
        }
        employeeRepository.updateEmployee(id,newEmployee);
    }
}
