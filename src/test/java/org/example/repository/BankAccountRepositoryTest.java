package org.example.repository;

import org.example.config.db.ConnectionPool;
import org.example.model.entity.BankAccount;
import org.example.model.entity.Employee;
import org.example.model.repository.BankAccountRepository;
import org.example.model.repository.EmployeeRepository;
import org.example.model.repository.impl.BankAccountRepositoryImpl;
import org.example.model.repository.impl.EmployeeRepositoryImpl;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class BankAccountRepositoryTest {
    private BankAccountRepository bankAccountRepository = new BankAccountRepositoryImpl();

    @BeforeAll
    static void createDB() {
        try (InputStream is = EmployeeRepositoryImplTest.class.getClassLoader().getResourceAsStream("schema.sql");
             Connection connection = ConnectionPool.getInstance().getConnection();
             Statement statement = connection.createStatement();
        ) {
            String sql = new String(is.readAllBytes());
            String emsSql = "insert into employees (name, surname, salary) values ('max', 'maximov', 200)";
            statement.execute(sql);
            statement.execute(emsSql);
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
            Statement statement = connection.createStatement()
        ) {
            String sql = "drop table department_employee, bankAccounts, employees, departments";
            statement.execute(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testSave(){
        BankAccount bankAccount = new BankAccount();
        bankAccount.setName("Account");
        bankAccount.setAmount(6000);

        EmployeeRepository employeeRepository = new EmployeeRepositoryImpl();
        bankAccount.setEmployee(employeeRepository.findById(1).get());

        int id = bankAccountRepository.save(bankAccount).getId();

        Optional<BankAccount> bankAccount1 = bankAccountRepository.findById(id);

        assertNotNull(bankAccount1);
        assertEquals(bankAccount.getName(), bankAccount1.get().getName());
    }

    @Test
    void findByIdTest(){
        Optional<BankAccount> bankAccount = Optional.of(new BankAccount());
        bankAccount.get().setId(1);
        bankAccount.get().setName("ba");
        bankAccount.get().setAmount(100);
        EmployeeRepository employeeRepository = new EmployeeRepositoryImpl();
        bankAccount.get().setEmployee(employeeRepository.findById(1).get());

        Optional<BankAccount> testBanAcc = bankAccountRepository.findById(1);

        assertNotNull(testBanAcc);
        assertEquals(bankAccount.get().getId(), testBanAcc.get().getId());
    }
}
