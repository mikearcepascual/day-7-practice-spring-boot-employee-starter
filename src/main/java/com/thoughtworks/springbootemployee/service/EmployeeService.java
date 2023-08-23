package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;

public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    public EmployeeService(EmployeeRepository mockedEmployeeRepository) {
        this.employeeRepository = mockedEmployeeRepository;
    }

    public Employee create(Employee employee) {
        return employeeRepository.addEmployee(employee);
    }
}
