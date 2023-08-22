package com.thoughtworks.springbootemployee.controller;

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
}
