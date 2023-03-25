package org.example.model.repository.impl;

import org.example.config.db.ConnectionPool;
import org.example.model.entity.Account;
import org.example.model.repository.AccountRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class AccountRepositoryImpl implements AccountRepository {
    @Override
    public void save(Account account) {
        try{
            Connection connection = ConnectionPool.getInstance().getConnection();
            String sql = "insert into account (name, amount) values (?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, account.getName());
            preparedStatement.setInt(2, account.getAmount());
            preparedStatement.executeUpdate();

        }catch (SQLException e){
            throw new RuntimeException();
        }
    }

    @Override
    public Account findById(Integer id) {
        return null;
    }

    @Override
    public boolean remove(Integer id) {
return false;
    }

    @Override
    public List<Account> findAll() {
        return null;
    }

    @Override
    public Account update(Account entity) {
        return null;
    }
}
