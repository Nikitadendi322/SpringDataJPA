package com.example.springdatajpa.Service;

import com.example.springdatajpa.Employee.Employee;
import com.example.springdatajpa.Employee.Position;
import com.example.springdatajpa.dto.EmployeeDto;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class EmployeeMapper {


    public Employee toEntity(EmployeeDto employeeDto){
        Employee employee=new Employee();
        employee.setName(employee.getName());
        employee.setSalary(employee.getSalary());
        return employee;
    }
    public EmployeeDto toDto(Employee employee){
        EmployeeDto employeeDto=new EmployeeDto();
        employeeDto.setId(employeeDto.getId());
        employeeDto.setName(employeeDto.getName());
        employeeDto.setSalary(employeeDto.getSalary());
        employeeDto.setPosition(employeeDto.getPosition());
        Optional.ofNullable(employee.getPosition())
                .map(Position::getPosition)
                .orElse(null);
        return employeeDto;
    }
}
