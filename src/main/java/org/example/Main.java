package org.example;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.example.model.repository.BankAccountRepository;
import org.example.model.repository.DepartmentRepository;
import org.example.model.repository.EmployeeRepository;
import org.example.model.repository.impl.BankAccountRepositoryImpl;
import org.example.model.repository.impl.DepartmentRepositoryImpl;
import org.example.model.repository.impl.EmployeeRepositoryImpl;

import java.sql.SQLException;

public class Main {
    static EmployeeRepository employeeRepository = new EmployeeRepositoryImpl();
    static DepartmentRepository departmentRepository = new DepartmentRepositoryImpl();
    static BankAccountRepository bankAccountRepository = new BankAccountRepositoryImpl();

    public static void main(String[] args) throws SQLException, JsonProcessingException {

        System.out.println("Hello world");
    }
}
