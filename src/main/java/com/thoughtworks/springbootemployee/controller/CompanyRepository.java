package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.exception.CompanyNotFoundException;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class CompanyRepository {
    private static final List<Company> companies = new ArrayList<>();

    static {
        companies.add(new Company(1L, "Spring"));
        companies.add(new Company(2L, "OOCL"));
        companies.add(new Company(3L, "COSCO"));
    }
    public List<Company> listAllCompanies() {
        return companies;
    }

    public Company findCompanyById(Long companyId) {
        return companies.stream()
                .filter(company -> company.getCompanyId().equals(companyId))
                .findFirst()
                .orElseThrow(CompanyNotFoundException::new);
    }
}
