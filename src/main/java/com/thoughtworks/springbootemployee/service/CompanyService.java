package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.repository.CompanyRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyService {
    private final CompanyRepository companyRepository;

    public CompanyService(CompanyRepository mockedCompanyRepository) {
        this.companyRepository = mockedCompanyRepository;
    }

    public List<Company> listAllCompanies() {
        return companyRepository.listAllCompanies();
    }

    public Company findCompanyById(Long companyId) {
        return companyRepository.findCompanyById(companyId);
    }

    public List<Company> listCompanyByPage(Long pageNumber, Long pageSize) {
        return companyRepository.listCompanyByPage(pageNumber, pageSize);
    }

    public Company addCompany(Company company) {
        return companyRepository.addCompany(company);
    }

    public void deleteCompany(Company company) {
        companyRepository.deleteCompany(company);
    }
}
