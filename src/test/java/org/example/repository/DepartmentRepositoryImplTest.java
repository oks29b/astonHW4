package org.example.repository;

import org.example.config.db.ConnectionPool;
import org.example.model.entity.Department;
import org.example.model.entity.Employee;
import org.example.model.repository.DepartmentRepository;
import org.example.model.repository.impl.DepartmentRepositoryImpl;
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

public class DepartmentRepositoryImplTest {

    DepartmentRepository departmentRepository = new DepartmentRepositoryImpl();

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
        Department department = new Department();
        department.setName("Department 1");
        department.setMaxSalary(100);
        department.setMinSalary(500000);

        Employee employee = new Employee();
        employee.setId(1);
        employee.setName("John");
        employee.setSurname("Doe");
        employee.setSalary(1000);
        List<Employee> employees = new ArrayList<>();
        employees.add(employee);

        department.setEmployees(employees);

        int id = departmentRepository.save(department).getId();

        Optional<Department> department1 = departmentRepository.findById(id);

        assertNotNull(department1);
        assertEquals(department.getName(), department1.get().getName());
    }

    @Test
    void findByIdTest(){
        Optional<Department> department = Optional.of(new Department());
        department.get().setId(1);
        department.get().setName("HR");
        department.get().setMaxSalary(100);
        department.get().setMinSalary(500000);

        Optional<Department> testDepartment = departmentRepository.findById(1);

        assertNotNull(testDepartment);
        assertEquals(department.get().getId(), testDepartment.get().getId());
    }

    @Test
    void updateTest(){
        Department department = new Department();
        department.setName("Department 1");
        department.setMaxSalary(100);
        department.setMinSalary(500000);

        Employee employee = new Employee();
        employee.setId(1);
        employee.setName("John");
        employee.setSurname("Doe");
        employee.setSalary(1000);
        List<Employee> employees = new ArrayList<>();
        employees.add(employee);

        department.setEmployees(employees);

        department = departmentRepository.save(department);

        // Verify that the employee was inserted
        assertNotNull(department.getId());

        // Update the employee's information
        department.setName("D2");
        department.setMaxSalary(100);
        department.setMinSalary(500000);
        department.setEmployees(employees);

        Department updatedDept = departmentRepository.update(department);

        // Verify that the employee was updated
        assertNotNull(updatedDept);
        assertEquals(department.getId(), updatedDept.getId());
        assertEquals(department.getName(), updatedDept.getName());
        assertEquals(department.getMaxSalary(), updatedDept.getMaxSalary());
        assertEquals(department.getMinSalary(), updatedDept.getMinSalary());
    }

    @Test
    void removeTest(){
        Department department = new Department();
        department.setName("Department 1");
        department.setMaxSalary(100);
        department.setMinSalary(500000);
        int id = departmentRepository.save(department).getId();

        // Verify that the employee was inserted
        assertNotNull(id);

        // Remove the employee from the database
        boolean removed = departmentRepository.remove(id);

        // Verify that the employee was removed
        assertTrue(removed);
    }
}
