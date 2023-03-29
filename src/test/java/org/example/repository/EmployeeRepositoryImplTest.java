package org.example.repository;

import org.example.config.db.ConnectionPool;
import org.example.model.entity.Department;
import org.example.model.entity.Employee;
import org.example.model.repository.EmployeeRepository;
import org.example.model.repository.impl.EmployeeRepositoryImpl;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class EmployeeRepositoryImplTest {
    EmployeeRepository employeeRepository = new EmployeeRepositoryImpl();

    @BeforeAll
    static void createDB() {
        try (InputStream is = EmployeeRepositoryImplTest.class.getClassLoader().getResourceAsStream("schema.sql");
             Connection connection = ConnectionPool.getInstance().getConnection();
             Statement statement = connection.createStatement();
        ) {
            String sql = new String(is.readAllBytes());
            String deptSql = "insert into departments (name, max_salary, min_salary) values ('hr', 100, 200)";
            statement.execute(sql);
            statement.execute(deptSql);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @AfterAll
    static  void removeDataFromDb(){
        try (
             Connection connection = ConnectionPool.getInstance().getConnection();
             Statement statement = connection.createStatement();
        ) {
            String sql = "drop table department_employee, bankAccounts, employees, departments";
            statement.execute(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testSave(){
        Employee employee = new Employee();
        employee.setName("John");
        employee.setSurname("Doe");
        employee.setSalary(1000);

        Department department = new Department();
        department.setId(1);
        department.setName("Department 1");
        List<Department> departments = new ArrayList<>();
        departments.add(department);
        employee.setDepartments(departments);

        int id = employeeRepository.save(employee).getId();

        Optional<Employee> employee1 = employeeRepository.findById(id);

        assertNotNull(employee1);
        assertEquals(employee.getName(), employee1.get().getName());
    }

    @Test
    void findByIdTest(){
        Optional<Employee> employee = Optional.of(new Employee());
        employee.get().setId(1);
        employee.get().setName("John");
        employee.get().setSurname("Doe");
        employee.get().setSalary(1000);

        Optional<Employee> testEmployee = employeeRepository.findById(1);

        assertNotNull(testEmployee);
        assertEquals(employee.get().getId(), testEmployee.get().getId());
    }

}
