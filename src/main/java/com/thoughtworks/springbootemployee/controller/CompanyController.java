package com.thoughtworks.springbootemployee.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/companies")
public class CompanyController {
    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @GetMapping
    public List<Company> listAllCompanies()
    {
        return companyRepository.listAllCompanies();
    }

    @GetMapping("/{companyId}")
    public Company findCompanyById(@PathVariable Long companyId){
        return companyRepository.findCompanyById(companyId);
    }

    @GetMapping("/{companyId}/employees")
    public List<Employee> listAllEmployees(@PathVariable Long companyId) {
        return employeeRepository.findEmployeesByCompanyId(companyId);
    }
}
