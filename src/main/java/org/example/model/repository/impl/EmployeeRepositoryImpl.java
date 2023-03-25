package org.example.model.repository.impl;

import org.example.config.db.ConnectionPool;
import org.example.model.entity.Department;
import org.example.model.entity.Employee;
import org.example.model.repository.EmployeeRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeRepositoryImpl implements EmployeeRepository {
    @Override
    public void save(Employee entity) {
        String sql = "insert into employees (name, surname, salary, department_id) values (?, ?, ?, ?)";
        try(Connection connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)){

            preparedStatement.setString(1, entity.getName());
            preparedStatement.setString(2, entity.getSurname());
            preparedStatement.setInt(3, entity.getSalary());
            preparedStatement.setInt(4, entity.getDepartment().getId());

            preparedStatement.executeUpdate();
        }catch (SQLException e){
            throw new RuntimeException();
        }
    }

    @Override
    public Employee findById(Integer empId) {
        String sql = "select * from employees as e left join departments as d on e.department_id=d.id where e.id=?";
        Employee employee =new Employee();
        try(
            Connection connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)
        ){
            preparedStatement.setInt(1,empId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                employee.setId(resultSet.getInt("id"));
                employee.setName(resultSet.getString("name"));
                employee.setSurname(resultSet.getString("surname"));
                employee.setSalary(resultSet.getInt("salary"));

                Department department = new Department();
                department.setId(resultSet.getInt("id"));
                department.setName(resultSet.getString("name"));
                department.setMaxSalary(resultSet.getInt("max_salary"));
                department.setMinSalary(resultSet.getInt("min_salary"));

                employee.setDepartment(department);
            }
        }catch (SQLException e){
            throw new RuntimeException();
        }
        return employee;
    }

    @Override
    public boolean remove(Integer empId) {
        String sql = "delete from employees where id=?";
        try(
            Connection connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)
        ){
            preparedStatement.setInt(1,empId);
            int result = preparedStatement.executeUpdate();
            return result > 0;
        }catch (SQLException e){
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    @Override
    public List<Employee> findAll() {
        String sql = "select * from employees as e left join departments as d on e.department_id=d.id";
        List<Employee> result = new ArrayList<>();
        try(
            Connection connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)
        ){
            ResultSet resultSet = preparedStatement.executeQuery(sql);
            while (resultSet.next()){
                Employee employee = new Employee();
                employee.setId(resultSet.getInt("id"));
                employee.setName(resultSet.getString("name"));
                employee.setSurname(resultSet.getString("surname"));
                employee.setSalary(resultSet.getInt("salary"));

                Department department = new Department();
                department.setId(resultSet.getInt("id"));
                department.setName(resultSet.getString("name"));
                department.setMaxSalary(resultSet.getInt("max_salary"));
                department.setMinSalary(resultSet.getInt("min_salary"));

                employee.setDepartment(department);

                result.add(employee);
            }
        }catch (SQLException e){
            throw new RuntimeException();
        }
        return result;
    }

    @Override
    public Employee update(Employee entity) {
        String sql = "update employees set name=?, " +
                     "surname=?, salary=?, department_id=(select id from departments where id=?) where id=?";
        try(
            Connection connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)
        ){
            preparedStatement.setString(1, entity.getName());
            preparedStatement.setString(2, entity.getSurname());
            preparedStatement.setInt(3, entity.getSalary());
            preparedStatement.setInt(4, entity.getDepartment().getId());
            preparedStatement.setInt(5, entity.getId());
            int result = preparedStatement.executeUpdate();
            if (result < 1){
                throw new SQLException("User doesn't update");
            }else {
                return entity;
            }
        }catch (SQLException e){
            throw new RuntimeException();
        }
    }
}
