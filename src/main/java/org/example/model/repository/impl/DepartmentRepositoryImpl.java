package org.example.model.repository.impl;

import org.example.config.db.ConnectionPool;
import org.example.model.entity.Department;
import org.example.model.repository.DepartmentRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DepartmentRepositoryImpl implements DepartmentRepository {
    @Override
    public Department save(Department entity){
        String sql2 = "insert into departments (name, max_salary, min_salary) values (?, ?, ?)";
        try(Connection connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql2, Statement.RETURN_GENERATED_KEYS)
        ){
            preparedStatement.setString(1, entity.getName());
            preparedStatement.setInt(2, entity.getMaxSalary());
            preparedStatement.setInt(3, entity.getMinSalary());
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if(resultSet.next()){
                entity.setId(resultSet.getInt(1));
            }
            return entity;
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
