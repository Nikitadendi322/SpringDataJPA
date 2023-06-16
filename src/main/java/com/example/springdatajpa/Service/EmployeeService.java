package com.example.springdatajpa.Service;


import com.example.springdatajpa.Employee.Employee;
import com.example.springdatajpa.Repository.EmployeeRepository;
import com.example.springdatajpa.dto.EmployeeDto;
import com.example.springdatajpa.exception.EmployeeNotFoundException;
import com.example.springdatajpa.exception.EmployeeNotValidException;

import jakarta.annotation.PostConstruct;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    private final EmployeeMapper employeeMapper;

    public EmployeeService(EmployeeRepository employeeRepository, EmployeeMapper employeeMapper) {

        this.employeeRepository = employeeRepository;
        this.employeeMapper = employeeMapper;
    }

    @PostConstruct
    public void init() {
        employeeRepository.deleteAll();
        employeeRepository.saveAll(
                List.of(
                        new Employee("Иван", 10_000),
                        new Employee("Катя", 30_000),
                        new Employee("Петя", 25_000)
                )
        );
    }

    public int getSumOfSalaries() {
        return employeeRepository.getSumOfSalaries();
    }

    public EmployeeDto getEmployeeWithMinSalary() {
        Page<EmployeeDto> page = employeeRepository.getEmployeeWithMinSalary(PageRequest.of(0, 1));
        if (page.isEmpty()) {
            return null;
        }
        return page.getContent().get(0);

    }

    public EmployeeDto getEmployeeWithMaxSalary() {
        List<EmployeeDto> employeeWithMaxSalary = getEmployeeWithHighestSalary();
        if (employeeWithMaxSalary.isEmpty()) {
            return null;
        }
        return employeeWithMaxSalary.get(0);
    }

    public List<EmployeeDto> getEmployeeWithSalaryHigherThanAverage() {
        double average = employeeRepository.getAverageOfSalaries();
        return getEmployeeWithSalaryHigherThan(average);
    }

    public List<EmployeeDto> createBatch(List<EmployeeDto> employees) {
        Optional<EmployeeDto> incorrectEmployee = employees.stream()
                .filter(employee -> employee.getSalary() <= 0 || employee.getName() == null
                        || employee.getName().isEmpty())
                .findFirst();
        if (incorrectEmployee.isPresent()) {
            throw new EmployeeNotValidException(incorrectEmployee.get());
        }
        return employeeRepository.saveAll(employees.stream()
                        .map(employeeMapper::toEntity)
                        .collect(Collectors.toList()))
                .stream()
                .map(employeeMapper::toDto)
                .collect(Collectors.toList());


    }

    public void update(int id, EmployeeDto employee) {
        Employee oldEmployee = employeeRepository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException(id));
        oldEmployee.setSalary(employee.getSalary());
        oldEmployee.setName(employee.getName());
        employeeRepository.save(oldEmployee);
    }

    public EmployeeDto get(int id) {
        return employeeRepository.findById(id)
                .map(employeeMapper::toDto)
                .map(employeeDto -> {
                    employeeDto.setPosition(null);
                    return employeeDto;
                })
                .orElseThrow(() -> new EmployeeNotFoundException(id));
    }

    public void delete(int id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException(id));
        employeeRepository.delete(employee);

    }

    public List<EmployeeDto> getEmployeeWithSalaryHigherThan(double salary) {
        return employeeRepository.findEmployeeBySalaryIsGreaterThan(salary).stream()
                .map(employeeMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<EmployeeDto> getEmployeeWithHighestSalary() {
        return employeeRepository.getEmployeeWithMaxSalary();
    }

    public List<EmployeeDto> getEmployee(@Nullable String position) {
        return Optional.ofNullable(position)
                .map(pos -> employeeRepository.findEmployeeByPosition_Position(pos))
                .orElseGet(employeeRepository::findAll)
                .stream()
                .map(employeeMapper::toDto)
                .collect(Collectors.toList());
    }

    public EmployeeDto getFullInfo(int id) {
        return employeeRepository.findById(id)
                .map(employeeMapper::toDto)
                .orElseThrow(() ->new EmployeeNotFoundException(id));
    }

    public List<EmployeeDto> getEmployeeFromPage(int page) {
        return employeeRepository.findAll(PageRequest.of(page,10)).stream()
                .map(employeeMapper::toDto)
                .collect(Collectors.toList());
    }
}



