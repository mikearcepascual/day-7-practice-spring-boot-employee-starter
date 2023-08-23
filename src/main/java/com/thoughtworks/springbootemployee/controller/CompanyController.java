package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.CompanyRepository;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import com.thoughtworks.springbootemployee.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/companies")
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    @Autowired
    private EmployeeRepository employeeRepository;

    @GetMapping
    public List<Company> listAllCompanies() {
        return companyService.listAllCompanies();
    }

    @GetMapping("/{companyId}")
    public Company findCompanyById(@PathVariable Long companyId) {
        return companyService.findCompanyById(companyId);
    }

    @GetMapping("/{companyId}/employees")
    public List<Employee> listAllEmployees(@PathVariable Long companyId) {
        return employeeRepository.findEmployeesByCompanyId(companyId);
    }

    @GetMapping(params = {"pageNumber", "pageSize"})
    public List<Company> listCompaniesByPage(@RequestParam Long pageNumber, @RequestParam Long pageSize) {
        return companyService.listCompanyByPage(pageNumber, pageSize);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Company addCompany(@RequestBody Company company) {
        return companyService.addCompany(company);
    }

    @PutMapping("/{companyId}")
    public Company updateCompany(@PathVariable Long companyId, @RequestBody Company newCompany) {
        Company company = companyService.findCompanyById(companyId);
        company.setCompanyName(newCompany.getCompanyName());
        return company;
    }

    @DeleteMapping("/{companyId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCompany(@PathVariable Long companyId) {
        Company company = companyService.findCompanyById(companyId);
        companyService.deleteCompany(company);
    }
}
