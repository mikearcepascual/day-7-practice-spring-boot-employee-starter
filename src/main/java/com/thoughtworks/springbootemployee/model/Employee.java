package com.thoughtworks.springbootemployee.model;

public class Employee {
    public static final int MAX_EMPLOYEE_AGE = 65;
    public static final int MIN_EMPLOYEE_AGE = 18;
    private boolean isEmployeeActive;
    private Long companyId;
    private Long id;
    private String name;
    private Integer age;
    private String gender;
    private Integer salary;

    public Employee(){

    }
    public Employee(Long id, String name, Integer age, String gender, Integer salary, Long companyId, boolean isEmployeeActive) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.salary = salary;
        this.companyId = companyId;
        this.isEmployeeActive = isEmployeeActive;
    }
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

    public void setAge(Integer age) {
        this.age = age;
    }

    public void setSalary(Integer salary) {
        this.salary = salary;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public boolean getEmployeeStatus()
    {
        return isEmployeeActive;
    }
}
