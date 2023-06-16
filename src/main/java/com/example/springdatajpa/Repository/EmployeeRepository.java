package com.example.springdatajpa.Repository;


import com.example.springdatajpa.Employee.Employee;
import com.example.springdatajpa.dto.EmployeeDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
    @Query("SELECT SUM (e.salary)FROM Employee e")
    int getSumOfSalaries();

    @Query("SELECT AVG (e.salary)FROM Employee e")
    double getAverageOfSalaries();

    @Query("SELECT new com.example.springdatajpa.dto.EmployeeDto(e.id,e.name,e.salary,p.position)FROM Employee e LEFT JOIN FETCH Position p WHERE e.salary=(SELECT MIN (e.salary)FROM Employee e)")
    Page<EmployeeDto> getEmployeeWithMinSalary(Pageable pageable);

    @Query("SELECT new com.example.springdatajpa.dto.EmployeeDto(e.id,e.name,e.salary,p.position)FROM Employee e LEFT JOIN FETCH Position p WHERE e.salary=(SELECT MAX(e.salary)FROM Employee e)")
    List<EmployeeDto> getEmployeeWithMaxSalary();

    List<Employee> findEmployeeBySalaryIsGreaterThan(double salary);

    List<Employee> findEmployeeByPosition_Position(String position);



}


