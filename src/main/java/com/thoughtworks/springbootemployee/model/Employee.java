package com.thoughtworks.springbootemployee.model;

import com.fasterxml.jackson.annotation.JsonCreator;

public class Employee {
    public static final int MAX_EMPLOYEE_AGE = 65;
    public static final int MIN_EMPLOYEE_AGE = 18;
    private Boolean isEmployeeActive;
    private final Long companyId;
    private Long id;
    private final String name;
    private Integer age;
    private final String gender;
    private Integer salary;

    public Employee(Long id, String name, Integer age, String gender, Integer salary, Long companyId) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.salary = salary;
        this.companyId = companyId;
    }
    @JsonCreator
    public Employee(String name, Integer age, String gender, Integer salary, Long companyId) {
        this.companyId = companyId;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.salary = salary;
    }

    public boolean hasInvalidAge() {
       return getAge() < MIN_EMPLOYEE_AGE || getAge() > MAX_EMPLOYEE_AGE;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getAge() {
        return age;
    }

    public String getGender() {
        return gender;
    }

    public Integer getSalary() {
        return salary;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public Boolean isEmployeeActive()
    {
        return isEmployeeActive;
    }


    public void merge(Employee employee) {
        salary = employee.getSalary();
        age = employee.getAge();
    }

    public void setEmployeeActive(boolean isEmployeeActive) {
        this.isEmployeeActive = isEmployeeActive;
    }
}
