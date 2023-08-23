package com.thoughtworks.springbootemployee.exception;

public class EmployeeInactiveException extends RuntimeException {
    public EmployeeInactiveException() {
        super("Employee is inactive");
    }
}
