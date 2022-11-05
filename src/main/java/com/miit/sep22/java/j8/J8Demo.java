package com.miit.sep22.java.j8;

import java.util.*;
import java.util.stream.Collectors;

public class J8Demo {

    public static void main(String[] args) {

        flatMapExample();
        peekMethodUsage();
        sortedMethodEmployeeByName();
        reduceMethodUsage();
        sortedByEmpNamesAndJoinAllNameWithComma();
        partitionBySalaryGreaterCertainAmountOrNot();

    }

    private static void partitionBySalaryGreaterCertainAmountOrNot() {

        System.out.println("------partitionBySalaryGreaterCertainAmountOrNot---------");
        Map<Boolean, List<Employee>> resultEmployeeMap = Arrays.stream(getEmployees())
                .collect(Collectors.partitioningBy(e -> e.getSalary() >= 300000.0));
        System.out.println("TRUE - Emp salary >= 300000.0 = "+resultEmployeeMap.get(true));
        System.out.println("FALSE - Emp salary >= 300000.0 = "+resultEmployeeMap.get(false));

        Map<Character, List<Employee>> groupByAlphabet = Arrays.stream(getEmployees()).collect(
                Collectors.groupingBy(e -> new Character(e.getName().charAt(0))));
        System.out.println(groupByAlphabet);

    }

    private static void sortedByEmpNamesAndJoinAllNameWithComma() {

        System.out.println("------sortedByEmpNamesAndJoinAllNameWithComma---------");
        String empNamesWithComma = Arrays.stream(getEmployees())
                .sorted(Comparator.comparing(Employee::getName))
                .map(Employee::getName)
                .collect(Collectors.joining(","))
                .toString();
        System.out.println(empNamesWithComma);

       
    }

    private static void reduceMethodUsage() {

        System.out.println("------reduceMethodUsage---------");
        double totalSalary = Arrays.stream(getEmployees())
                .map(Employee::getSalary)
                .reduce(0.0, Double::sum);

        System.out.println("Total salary = "+totalSalary);
       
    }

    static void flatMapExample() {

        System.out.println("------flatMapExample---------");
        List<String> list = getListOfNames().stream()
                .flatMap(Collection::stream)
                .collect(Collectors.toList());

        System.out.println("Employee flatMap = "+list);
       

    }


    static void peekMethodUsage() { //Make note this is not terminal operation, and it will perform on each element of stream

        System.out.println("-------peekMethodUsage--------");

        Employee[] arrayOfEmps = getEmployees();
        List<Employee> empList = Arrays.asList(arrayOfEmps);

        empList.stream()
                .peek(e -> e.salaryIncrement(10.0))
                .peek(System.out::println)
                .collect(Collectors.toList());
       

    }


    static void sortedMethodEmployeeByName() { //Make note this is not terminal operation, and it will perform on each element of stream
        Employee[] arrayOfEmps = getEmployees();

        List<Employee> empList = Arrays.asList(arrayOfEmps);
        System.out.println("------sortedMethodEmployeeByName---------");
        empList.stream()
                .sorted(Comparator.comparing(Employee::getName))
                .collect(Collectors.toList())
                .forEach(e -> System.out.println(e.getName()));
       
    }

    private static Employee[] getEmployees() {
        Employee[] arrayOfEmps = {
                new Employee(1, "Jeff Bezos", 100000.0),
                new Employee(2, "Bill Gates", 200000.0),
                new Employee(3, "Mark Zuckerberg", 300000.0),
                new Employee(4, "Adani", 400000.0),
                new Employee(5, "Ambani", 200000.0)
        };
        return arrayOfEmps;
    }

    static List<List<String>> getListOfNames() {
        return Arrays.asList(
                Arrays.asList("Jeff", "Bezos"),
                Arrays.asList("Bill", "Gates"),
                Arrays.asList("Mark", "Zuckerberg"));
    }
}
