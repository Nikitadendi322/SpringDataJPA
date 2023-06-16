package com.example.springdatajpa.exception;


import com.example.springdatajpa.Employee.Employee;
import com.example.springdatajpa.dto.EmployeeDto;


public class EmployeeNotValidException extends RuntimeException {

    private final EmployeeDto employee;

    public EmployeeNotValidException(EmployeeDto employee) {
        this.employee = employee;
    }

    public EmployeeDto getEmployee() {
        return employee;
    }
}
