package com.example.springdatajpa.dto;

public class EmployeeDto {
    private int id;
    private String name;
    private int salary;
    private String position;

    public EmployeeDto(int id, String name, int salary,String position) {
        this.id = id;
        this.name = name;
        this.salary = salary;
        this.position=position;
    }

    public EmployeeDto(){
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    @Override
    public String toString() {
        return "EmployeeDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", salary=" + salary +
                '}';
    }
}
