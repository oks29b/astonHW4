package org.example.model.repository.impl;

import org.example.config.db.ConnectionPool;
import org.example.model.entity.Department;
import org.example.model.entity.Employee;
import org.example.model.repository.DepartmentRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DepartmentRepositoryImpl implements DepartmentRepository {
    @Override
    public Department save(Department entity){
//        String sql2 = "insert into departments (name, max_salary, min_salary) values (?, ?, ?)";
        String departmentSql = "insert into departments (name, max_salary, min_salary) values (?, ?, ?)";
        String empsSql = "insert into department_employee (emloyee_id, department_id) values (?, ?)";

        Connection connection =  ConnectionPool.getInstance().getConnection();

        try(connection;
            PreparedStatement departmentPreparedStatement = connection.prepareStatement(departmentSql, Statement.RETURN_GENERATED_KEYS);
            PreparedStatement employeePreparedStatement = connection.prepareStatement(empsSql)
        ){

            connection.setAutoCommit(false);

            departmentPreparedStatement.setString(1, entity.getName());
            departmentPreparedStatement.setInt(2, entity.getMaxSalary());
            departmentPreparedStatement.setInt(3, entity.getMinSalary());
            departmentPreparedStatement.executeUpdate();

            ResultSet resultSet = departmentPreparedStatement.getGeneratedKeys();

            if(resultSet.next()){
                entity.setId(resultSet.getInt(1));
            }

            for(Employee employee : entity.getEmployees()){
                employeePreparedStatement.setInt(1, employee.getId());
                employeePreparedStatement.setInt(2, entity.getId());
                employeePreparedStatement.executeUpdate();
            }

            connection.commit();

            return entity;
        }catch (SQLException e){
            e.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            throw new RuntimeException(e);
        }
    }

    @Override
    public Department findById(Integer id) {
        String deptSql = "select * from departments where id=?";
        String empsSql = "select e.id, e.name, e.surname, e.salary from department_employee as de left join employees as e on de.emloyee_id=e.id where de.department_id=?;";

        Department department = new Department();
        List<Employee> employeeList = new ArrayList<>();

        try(Connection connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement empsPrepStat = connection.prepareStatement(empsSql);
            PreparedStatement deptsPrepStat = connection.prepareStatement(deptSql)
        ){
            deptsPrepStat.setInt(1, id);
            empsPrepStat.setInt(1, id);

            ResultSet deptsResultSet = deptsPrepStat.executeQuery();
            while(deptsResultSet.next()){
                department.setId(deptsResultSet.getInt("id"));
                department.setName(deptsResultSet.getString("name"));
                department.setMaxSalary(deptsResultSet.getInt("max_salary"));
                department.setMinSalary(deptsResultSet.getInt("min_salary"));
            }

            ResultSet empsResultSet = empsPrepStat.executeQuery();
            while (empsResultSet.next()){
                Employee employee = new Employee();
                employee.setId(empsResultSet.getInt("id"));
                employee.setName(empsResultSet.getString("name"));
                employee.setSurname(empsResultSet.getString("surname"));
                employee.setSalary(empsResultSet.getInt("salary"));
                employeeList.add(employee);
            }

            department.setEmployees(employeeList);
        }catch(SQLException e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return department;
    }

    @Override
    public boolean remove(Integer id) {
        String sql = "delete from departments where id=?";
        try(
            Connection connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)
        ){
            preparedStatement.setInt(1, id);
            int result = preparedStatement.executeUpdate();
            return result > 0;
        }catch (SQLException e){
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    @Override
    public List<Department> findAll() {
        List<Department> result = new ArrayList<>();
//        try{
//            Connection connection = ConnectionPool.getInstance().getConnection();
//            String sql = "select * from departments";
//            PreparedStatement preparedStatement = connection.prepareStatement(sql);
//            ResultSet resultSet = preparedStatement.executeQuery();
//            while (resultSet.next()){
//                Department department = new Department();
//                department.setId(resultSet.getInt("id"));
//                department.setName(resultSet.getString("name"));
//                department.setMaxSalary(resultSet.getInt("max_salary"));
//                department.setMinSalary(resultSet.getInt("min_salary"));
//                result.add(department);
//            }
//            preparedStatement.close();
//            connection.close();
//        }catch (SQLException e){
//            throw new RuntimeException();
//        }
        return result;
    }

    @Override
    public Department update(Department entity) {
        String departmentSql = "update employees set name=?, max_salary=?, min_salary=? where id=?";
        String empsSql = "update department_employee set emloyee_id=?, department_id=? where employee_id=?";

        Connection connection =  ConnectionPool.getInstance().getConnection();

        try(connection;
            PreparedStatement deptPreparedStatement = connection.prepareStatement(departmentSql);
            PreparedStatement empsPreparedStatement = connection.prepareStatement(empsSql)
        ){

            connection.setAutoCommit(false);

            deptPreparedStatement.setString(1, entity.getName());
            deptPreparedStatement.setInt(2, entity.getMaxSalary());
            deptPreparedStatement.setInt(3, entity.getMinSalary());
            deptPreparedStatement.executeUpdate();

            for(Employee employee : entity.getEmployees()){
                deptPreparedStatement.setInt(1, employee.getId());
                deptPreparedStatement.setInt(2, entity.getId());
                deptPreparedStatement.executeUpdate();
            }

            connection.commit();

            return entity;
        }catch (SQLException e){
            e.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            throw new RuntimeException(e);
        }
    }
}
