package org.example;

import org.example.model.entity.BankAccount;
import org.example.model.entity.Department;
import org.example.model.entity.Employee;
import org.example.model.repository.BankAccountRepository;
import org.example.model.repository.DepartmentRepository;
import org.example.model.repository.EmployeeRepository;
import org.example.model.repository.impl.BankAccountRepositoryImpl;
import org.example.model.repository.impl.DepartmentRepositoryImpl;
import org.example.model.repository.impl.EmployeeRepositoryImpl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class Main {
    static EmployeeRepository employeeRepository = new EmployeeRepositoryImpl();

    static DepartmentRepository departmentRepository = new DepartmentRepositoryImpl();
    static BankAccountRepository bankAccountRepository = new BankAccountRepositoryImpl();

    public static void main(String[] args) throws SQLException {

//        BankAccount bankAccount = new BankAccount();
//        bankAccount.setId(1);
//        bankAccount.setName("pervy");
//        bankAccount.setAmount(100);
//
//        BankAccount bankAccount2 = new BankAccount();
//        bankAccount2.setId(2);
//        bankAccount2.setName("vtory");
//        bankAccount2.setAmount(200);
//        bankAccount2.setEmployee(employeeRepository.findById(5));
//
//        List<BankAccount> bankAccounts = new ArrayList<>();
//        bankAccounts.add(bankAccount);
//        bankAccounts.add(bankAccount2);
//
//        Department department = new Department();
//        department.setId(1);
//        department.setName("hr");
//        department.setMaxSalary(1000);
//        department.setMinSalary(100);
//
//        List<Department> departments = new ArrayList<>();
//        departments.add(department);
//
//        Employee employee = new Employee();
//        employee.setName("max");
//        employee.setSurname("maximov");
//        employee.setSalary(500);
//        employee.setDepartments(departments);
//
//        departmentRepository.save(department);
//
//        bankAccount.setEmployee(employeeRepository.save(employee));
//        bankAccountRepository.save(bankAccount);



//            System.out.println(bankAccountRepository.update(bankAccount2));

        System.out.println(employeeRepository.findAll());
        System.out.println("Hello world!");
    }
}
