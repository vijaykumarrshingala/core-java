package com.miit.sep22.java.account.test;

import com.miit.sep22.java.employee.dto.Address;
import com.miit.sep22.java.employee.dto.Employee;
import com.miit.sep22.java.employee.repository.EmployeeRepository;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class EmployeeApp {

    public static void main(String[] args) {

        String empId = "avc";

        //Error File

        Employee employee = new Employee("f",1,2.0,null);
        try {
            employee.setEmployeeId(Integer.parseInt(empId));
        } catch (NumberFormatException ex) {
            System.out.println(ex.getMessage());
            //write to file - error message with detail

        } catch(Exception ex) {
            System.out.println(ex.getMessage());
        }

    }


}