package org.example;

import org.example.model.entity.Department;
import org.example.model.entity.Employee;
import org.example.model.repository.EmployeeRepository;
import org.example.model.repository.impl.EmployeeRepositoryImpl;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        Department department = new Department("hr", 200, 5000);
        department.setId(2);

        Employee employee = new Employee("max", "petrov", 4500, department);
        employee.setId(3);


        EmployeeRepository employeeRepository = new EmployeeRepositoryImpl();
        System.out.println(employeeRepository.update(employee));

        System.out.println("Hello world!");
    }
}
