package org.example.model.repository.impl;

import org.example.config.db.ConnectionPool;
import org.example.model.entity.BankAccount;
import org.example.model.entity.Department;
import org.example.model.entity.Employee;
import org.example.model.repository.EmployeeRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeRepositoryImpl implements EmployeeRepository {
    @Override
    public Employee save(Employee entity) {
        String employeeSql = "insert into employees (name, surname, salary) values (?, ?, ?)";
        String deptSql = "insert into department_employee (emloyee_id, department_id) values (?, ?)";

        Connection connection =  ConnectionPool.getInstance().getConnection();

        try(connection;
            PreparedStatement emplPreparedStatement = connection.prepareStatement(employeeSql, Statement.RETURN_GENERATED_KEYS);
            PreparedStatement deptPreparedStatement = connection.prepareStatement(deptSql)
            ){

            connection.setAutoCommit(false);

            emplPreparedStatement.setString(1, entity.getName());
            emplPreparedStatement.setString(2, entity.getSurname());
            emplPreparedStatement.setInt(3, entity.getSalary());
            emplPreparedStatement.executeUpdate();

            ResultSet resultSet = emplPreparedStatement.getGeneratedKeys();

            if(resultSet.next()){
                entity.setId(resultSet.getInt(1));
            }

            for(Department department : entity.getDepartments()){
                deptPreparedStatement.setInt(1, entity.getId());
                deptPreparedStatement.setInt(2, department.getId());
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

    @Override
    public Employee findById(Integer id) {
        String employeeSql = "select * from employees where id=?";
        String departmentSql = "select d.id, d.name, min_salary, max_salary from department_employee as de left join departments as d on de.department_id=d.id where de.emloyee_id=?";
        String bankAccontSql = "select ba.id, ba.name, amount from bankAccounts as ba right join employees as e on ba.employee_id=e.id where employee_id=?";
        Employee employee = new Employee();
        List<Department> departmentList = new ArrayList<>();
        List<BankAccount> bankAccountList = new ArrayList<>();

        try(Connection connection =  ConnectionPool.getInstance().getConnection();
                PreparedStatement emplPreparedStatement = connection.prepareStatement(employeeSql);
                PreparedStatement deptPreparedStatement = connection.prepareStatement(departmentSql);
                PreparedStatement bankAccPreparesStatement = connection.prepareStatement(bankAccontSql);
        ){
            emplPreparedStatement.setInt(1,id);
            deptPreparedStatement.setInt(1, id);
            bankAccPreparesStatement.setInt(1, id);

            ResultSet resultSetEmps = emplPreparedStatement.executeQuery();
            while (resultSetEmps.next()) {
                employee.setId(resultSetEmps.getInt("id"));
                employee.setName(resultSetEmps.getString("name"));
                employee.setSurname(resultSetEmps.getString("surname"));
                employee.setSalary(resultSetEmps.getInt("salary"));
            }

            ResultSet resultSetDepts = deptPreparedStatement.executeQuery();
            while (resultSetDepts.next()){
                Department department = new Department();
                department.setId(resultSetDepts.getInt("id"));
                department.setName(resultSetDepts.getString("name"));
                department.setMinSalary(resultSetDepts.getInt("min_salary"));
                department.setMaxSalary(resultSetDepts.getInt("max_salary"));
                departmentList.add(department);
            }

            ResultSet resultSetBanAcc = bankAccPreparesStatement.executeQuery();
            while(resultSetBanAcc.next()){
                BankAccount bankAccount = new BankAccount();
                bankAccount.setId(resultSetBanAcc.getInt("id"));
                bankAccount.setName(resultSetBanAcc.getString("name"));
                bankAccount.setAmount(resultSetBanAcc.getInt("amount"));
                bankAccountList.add(bankAccount);
            }

            employee.setDepartments(departmentList);
            employee.setBankAccounts(bankAccountList);

        }catch (SQLException e){
            e.printStackTrace();
            throw new RuntimeException(e);
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
//        String sql = "select * from employees as e left join departments as d on e.department_id=d.id";
        List<Employee> result = new ArrayList<>();
//        try(
//            Connection connection = ConnectionPool.getInstance().getConnection();
//            PreparedStatement preparedStatement = connection.prepareStatement(sql)
//        ){
//            ResultSet resultSet = preparedStatement.executeQuery(sql);
//            while (resultSet.next()){
//                Employee employee = new Employee();
//                employee.setId(resultSet.getInt("id"));
//                employee.setName(resultSet.getString("name"));
//                employee.setSurname(resultSet.getString("surname"));
//                employee.setSalary(resultSet.getInt("salary"));
//
//                Department department = new Department();
//                department.setId(resultSet.getInt("id"));
//                department.setName(resultSet.getString("name"));
//                department.setMaxSalary(resultSet.getInt("max_salary"));
//                department.setMinSalary(resultSet.getInt("min_salary"));
//
//                employee.setDepartment(department);
//
//                result.add(employee);
//            }
//        }catch (SQLException e){
//            throw new RuntimeException();
//        }
        return result;
    }

    @Override
    public Employee update(Employee entity) {
        String employeeSql = "update employees set name=?, surname=?, salary=? where id=?";
        String deptSql = "update department_employee set emloyee_id=?, department_id=? where employee_id=?";

        Connection connection =  ConnectionPool.getInstance().getConnection();

        try(connection;
            PreparedStatement emplPreparedStatement = connection.prepareStatement(employeeSql);
            PreparedStatement deptPreparedStatement = connection.prepareStatement(deptSql)
        ){

            connection.setAutoCommit(false);

            emplPreparedStatement.setString(1, entity.getName());
            emplPreparedStatement.setString(2, entity.getSurname());
            emplPreparedStatement.setInt(3, entity.getSalary());
            emplPreparedStatement.setInt(4, entity.getId());
            emplPreparedStatement.executeUpdate();

            for(Department department : entity.getDepartments()){
                deptPreparedStatement.setInt(1, entity.getId());
                deptPreparedStatement.setInt(2, department.getId());
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
