package org.example.model.repository.impl;

import org.example.config.db.ConnectionPool;
import org.example.model.entity.Department;
import org.example.model.repository.DepartmentRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DepartmentRepositoryImpl implements DepartmentRepository {
    @Override
    public void save(Department entity){
        try{
            Connection connection = ConnectionPool.getInstance().getConnection();
            String sql2 = "insert into departments (name, max_salary, min_salary) values (?, ?, ?)";
            PreparedStatement preparedStatement2 = connection.prepareStatement(sql2);
            preparedStatement2.setString(1, entity.getName());
            preparedStatement2.setInt(2, entity.getMaxSalary());
            preparedStatement2.setInt(3, entity.getMinSalary());
            preparedStatement2.executeUpdate();
            preparedStatement2.close();
            connection.close();
        }catch (SQLException e){
            throw new RuntimeException();
        }

    }

    @Override
    public Department findById(Integer id) {
        return null;
    }

    @Override
    public boolean remove(Integer id) {
        return false;
    }

    @Override
    public List<Department> findAll() {
        List<Department> result = new ArrayList<>();
        try{
            Connection connection = ConnectionPool.getInstance().getConnection();
            String sql = "select * from departments";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                Department department = new Department();
                department.setId(resultSet.getInt("id"));
                department.setName(resultSet.getString("name"));
                department.setMaxSalary(resultSet.getInt("max_salary"));
                department.setMinSalary(resultSet.getInt("min_salary"));
                result.add(department);
            }
            preparedStatement.close();
            connection.close();
        }catch (SQLException e){
            throw new RuntimeException();
        }
        return result;
    }

    @Override
    public Department update(Department entity) {
        return null;
    }
}
