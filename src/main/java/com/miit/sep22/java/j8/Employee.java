package com.miit.sep22.java.j8;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Setter
@Getter
public class Employee {

    int id;
    String name;
    double salary;

    public void salaryIncrement(double v) {
        this.salary = this.salary  + this.salary * (v/100);
    }

    @Override
    public String toString() {
        return this.id +" : "+this.salary;
    }
}
