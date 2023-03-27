package org.example.model.repository.impl;

import org.example.config.db.ConnectionPool;
import org.example.model.entity.BankAccount;
import org.example.model.entity.Employee;
import org.example.model.repository.BankAccountRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BankAccountRepositoryImpl implements BankAccountRepository {
    @Override
    public BankAccount save(BankAccount account) {
        String sql = "insert into bankAccounts (name, amount, employee_id) values (?, ?, ?)";
        try(Connection connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){

            preparedStatement.setString(1, account.getName());
            preparedStatement.setInt(2, account.getAmount());
            preparedStatement.setInt(3, account.getEmployee().getId());
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if(resultSet.next()){
                account.setId(resultSet.getInt(1));
            }
            return account;
        }catch (SQLException e){
            throw new RuntimeException();
        }
    }

    @Override
    public BankAccount findById(Integer id) {
        String sql = "select * from bankAccounts as ba left join employees as e on ba.employee_id=e.id where ba.id=?";
        BankAccount bankAccount = new BankAccount();
        try(
            Connection connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)
        ){
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                bankAccount.setId(resultSet.getInt("id"));
                bankAccount.setName(resultSet.getString("name"));
                bankAccount.setAmount(resultSet.getInt("amount"));

                Employee employee = new Employee();
                employee.setId(resultSet.getInt("id"));
                employee.setName(resultSet.getString("name"));
                employee.setSurname(resultSet.getString("surname"));
                employee.setSalary(resultSet.getInt("salary"));

                bankAccount.setEmployee(employee);
            }
        }catch (SQLException e){
            throw new RuntimeException();
        }
        return bankAccount;
    }

    @Override
    public boolean remove(Integer id) {
        String sql = "delete from bankAccounts where id=?";
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
    public List<BankAccount> findAll() {
        String sql = "select * from bankAccounts as ba left join employees as e on ba.employee_id=e.id";
        List<BankAccount> result = new ArrayList<>();
        try(
            Connection connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)
        ){
            ResultSet resultSet = preparedStatement.executeQuery(sql);
            while (resultSet.next()){
                BankAccount bankAccount = new BankAccount();
                bankAccount.setId(resultSet.getInt("id"));
                bankAccount.setName(resultSet.getString("name"));
                bankAccount.setAmount(resultSet.getInt("amount"));

                Employee employee = new Employee();
                employee.setId(resultSet.getInt("id"));
                employee.setName(resultSet.getString("name"));
                employee.setSurname(resultSet.getString("surname"));
                employee.setSalary(resultSet.getInt("salary"));

                bankAccount.setEmployee(employee);

                result.add(bankAccount);
            }
        }catch (SQLException e){
            throw new RuntimeException();
        }
        return result;
    }

    @Override
    public BankAccount update(BankAccount entity) {
        String sql = "update bankAccounts set name=?, amount=? where id=?";
        try(
            Connection connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)
        ){
            preparedStatement.setString(1, entity.getName());
            preparedStatement.setInt(2, entity.getAmount());
            preparedStatement.setInt(3, entity.getId());
            int result = preparedStatement.executeUpdate();
            if (result < 1){
                throw new SQLException("Account doesn't update");
            }else {
                return entity;
            }
        }catch (SQLException e){
            throw new RuntimeException();
        }
    }
}
