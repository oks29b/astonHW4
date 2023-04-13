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

import static org.junit.jupiter.api.Assertions.*;

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
            String empsSql = "insert into employees (name, surname, salary) values ('John', 'Doe', 500)";
            statement.execute(sql);
            statement.execute(deptSql);
            statement.execute(empsSql);
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
            Statement statement = connection.createStatement()
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

    @Test
    void updateTest(){
        Employee employee = new Employee();
        employee.setName("John");
        employee.setSurname("Marks");
        employee.setSalary(1000);

        Department department = new Department();
        department.setId(1);
        department.setName("Department 1");
        List<Department> departments = new ArrayList<>();
        departments.add(department);
        employee.setDepartments(departments);

        employee = employeeRepository.save(employee);

        // Verify that the employee was inserted
        assertNotNull(employee.getId());

        // Update the employee's information
        employee.setName("Jane");
        employee.setSurname("Doe");
        employee.setSalary(60000);
        employee.setDepartments(departments);

        Employee updatedEmpl = employeeRepository.update(employee);

        // Verify that the employee was updated
        assertNotNull(updatedEmpl);
        assertEquals(employee.getId(), updatedEmpl.getId());
        assertEquals(employee.getName(), updatedEmpl.getName());
        assertEquals(employee.getSurname(), updatedEmpl.getSurname());
        assertEquals(employee.getSalary(), updatedEmpl.getSalary());
    }

    @Test
    void removeTest(){
        Employee employee = new Employee();
        employee.setName("John");
        employee.setSurname("Doe");
        employee.setSalary(1000);
        int id = employeeRepository.save(employee).getId();

        // Verify that the employee was inserted
        assertNotNull(id);

        // Remove the employee from the database
        boolean removed = employeeRepository.remove(id);

        // Verify that the employee was removed
        assertTrue(removed);
    }

}
