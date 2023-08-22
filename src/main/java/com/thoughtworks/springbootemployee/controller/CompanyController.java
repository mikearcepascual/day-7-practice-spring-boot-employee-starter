package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.exception.CompanyNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/companies")
public class CompanyController {
    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @GetMapping
    public List<Company> listAllCompanies() {
        return companyRepository.listAllCompanies();
    }

    @GetMapping("/{companyId}")
    public Company findCompanyById(@PathVariable Long companyId) {
        return companyRepository.findCompanyById(companyId);
    }

    @GetMapping("/{companyId}/employees")
    public List<Employee> listAllEmployees(@PathVariable Long companyId) {
        return employeeRepository.findEmployeesByCompanyId(companyId);
    }

    @GetMapping(params = {"pageNumber", "pageSize"})
    public List<Company> listCompanyByPage(@RequestParam Long pageNumber, @RequestParam Long pageSize) {
        return companyRepository.listCompanyByPage(pageNumber, pageSize);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Company addCompany(@RequestBody Company company){
        return companyRepository.addCompany(company);
    }

    @PutMapping("/{companyId}")
    public Company updateCompany(@PathVariable Long companyId, @RequestBody Company newCompany){
        Company company = companyRepository.findCompanyById(companyId);
        company.setCompanyName(newCompany.getCompanyName());
        return company;
    }

    @DeleteMapping("/{companyId}")
    public void deleteCompany(@PathVariable Long companyId){
        Company company = companyRepository.findCompanyById(companyId);
        if(company == null){ throw new CompanyNotFoundException(); }
        companyRepository.deleteCompany(company);
    }
}
