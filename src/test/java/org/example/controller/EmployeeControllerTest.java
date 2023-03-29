package org.example.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.config.db.ConnectionPool;
import org.example.model.entity.Employee;
import org.example.model.repository.EmployeeRepository;
import org.example.repository.EmployeeRepositoryImplTest;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.Mock;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmployeeControllerTest {
    EmployeeController employeeController = new EmployeeController();

    @Mock
    HttpServletRequest req;
    @Mock
    HttpServletResponse resp;
    private ObjectMapper objectMapper = new ObjectMapper();
    @Mock
    private EmployeeRepository employeeRepository;

    @BeforeAll
    static void createDB() {
        try (InputStream is = EmployeeRepositoryImplTest.class.getClassLoader().getResourceAsStream("schema.sql");
             Connection connection = ConnectionPool.getInstance().getConnection();
             Statement statement = connection.createStatement();
        ) {
            String sql = new String(is.readAllBytes());
            String deptSql = "insert into employees (name, surname, salary) values ('John', 'Doe', 1000)";
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
    void doGet() throws IOException, ServletException {
//        Employee mockEmployee = new Employee();
//        mockEmployee.setId(1);
//        mockEmployee.setName("John");
//        mockEmployee.setSurname("Doe");
//        mockEmployee.setSalary(1000);
//        mockEmployee.setDepartments(new ArrayList<>());
//        mockEmployee.setBankAccounts(new ArrayList<>());

//        when(employeeRepository.findById(1)).thenReturn(Optional.of(mockEmployee));

        when(req.getParameter("id")).thenReturn("1");

        PrintWriter writer = mock(PrintWriter.class);
        when(resp.getWriter()).thenReturn(writer);

        // Act
        employeeController.doGet(req, resp);

        // Assert
        verify(resp).setContentType("application/json");
        verify(resp).setCharacterEncoding("UTF-8");
        verify(writer).print("{\"id\":1,\"name\":\"John\",\"surname\":\"Doe\",\"salary\":1000,\"departments\":[],\"bankAccounts\":[]}");
        verify(writer).flush();
        verifyNoMoreInteractions(resp, writer);
    }

    @Test
    void doPost() {
    }
}
