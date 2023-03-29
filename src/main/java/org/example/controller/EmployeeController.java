package org.example.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.model.entity.Employee;
import org.example.model.repository.EmployeeRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet("/employees")
public class EmployeeController extends HttpServlet {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final EmployeeRepository employeeRepository;

    public EmployeeController(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        List<Employee> employees = employeeRepository.findAll();
//        String employeeJson = objectMapper.writeValueAsString(employees);
//        PrintWriter out = resp.getWriter();
//        resp.setContentType("application/json");
//        resp.setCharacterEncoding("UTF-8");
//        out.print(employeeJson);
//        out.flush();

        PrintWriter out = resp.getWriter();
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        out.print("employeeJson");
        out.flush();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try(BufferedReader reader = req.getReader()){
            String body = reader.lines().collect(Collectors.joining(" "));
            Employee employee = objectMapper.readValue(body, Employee.class);
            Employee emps = employeeRepository.save(employee);
            PrintWriter writer = resp.getWriter();
            resp.setStatus(HttpServletResponse.SC_OK);
            resp.setContentType("application/json");
            writer.print(objectMapper.writeValueAsString(emps));
            writer.flush();
        }
    }
}
