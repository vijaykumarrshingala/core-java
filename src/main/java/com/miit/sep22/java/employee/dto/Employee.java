package com.miit.sep22.java.employee.dto;


import lombok.Data;

@Data
public class Employee implements Cloneable {

    private String firstName;
    private Integer employeeId;
    private double salary;
    private Address address;


    public Employee(String firstName, Integer employeeId, double salary, Address address) {
        this.firstName = firstName;
        this.employeeId = employeeId;
        this.salary = salary;
        this.address = address;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
