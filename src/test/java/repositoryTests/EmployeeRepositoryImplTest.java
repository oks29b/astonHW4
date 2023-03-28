package repositoryTests;

import org.example.config.db.ConnectionPool;
import org.example.model.entity.Department;
import org.example.model.entity.Employee;
import org.example.model.repository.EmployeeRepository;
import org.example.model.repository.impl.EmployeeRepositoryImpl;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class EmployeeRepositoryImplTest {
    @Mock
    private ConnectionPool connectionPool;

    @Mock
    private Connection connection;

    @Mock
    private PreparedStatement preparedStatement1;

    @Mock
    private PreparedStatement preparedStatement2;

    @Mock
    private ResultSet resultSet;

    @InjectMocks
    private EmployeeRepositoryImpl employeeRepository;

//    @Test
//    void testSave() throws Exception {
//        // Arrange
//        MockitoAnnotations.openMocks(this);
//        Employee employee = new Employee();
//        employee.setId(1);
//        employee.setName("John");
//        employee.setSurname("Doe");
//        employee.setSalary(1000);
//
//        Department department = new Department();
//        department.setId(1);
//        department.setName("Department 1");
//        List<Department> departments = new ArrayList<>();
//        departments.add(department);
//        employee.setDepartments(departments);
//
//        when(ConnectionPool.getInstance().getConnection()).thenReturn(connection);
//        when(connection.prepareStatement(anyString(), eq(Statement.RETURN_GENERATED_KEYS))).thenReturn(preparedStatement1);
//        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement2);
//        when(preparedStatement1.executeUpdate()).thenReturn(1);
//        when(preparedStatement1.getGeneratedKeys()).thenReturn(resultSet);
//        when(resultSet.next()).thenReturn(true);
//        when(resultSet.getInt(1)).thenReturn(1);
//
//        // Act
//        Employee savedEmployee = employeeRepository.save(employee);
//
//        // Assert
//        assertNotNull(savedEmployee);
//        assertEquals(employee.getId(), savedEmployee.getId());
//        assertEquals(employee.getName(), savedEmployee.getName());
//        assertEquals(employee.getSurname(), savedEmployee.getSurname());
//        assertEquals(employee.getSalary(), savedEmployee.getSalary());
//        verify(ConnectionPool.getInstance().getConnection());
//        verify(connection).setAutoCommit(false);
//        verify(preparedStatement1).setString(1, employee.getName());
//        verify(preparedStatement1).setString(2, employee.getSurname());
//        verify(preparedStatement1).setInt(3, employee.getSalary());
//        verify(preparedStatement1).executeUpdate();
//        verify(preparedStatement1).getGeneratedKeys();
//        verify(resultSet).next();
//        verify(resultSet).getInt(1);
//        verify(preparedStatement2).setInt(1, employee.getId());
//        verify(preparedStatement2).setInt(2, department.getId());
//        verify(preparedStatement2).executeUpdate();
//        verify(connection).commit();
//        verify(preparedStatement1).close();
//        verify(preparedStatement2).close();
//        verify(connection).close();
//    }


}
