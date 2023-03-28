package repositoryTests;

import org.example.model.entity.BankAccount;
import org.example.model.repository.impl.BankAccountRepositoryImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BankAccountRepositoryTest {
//    private static BankAccountRepositoryImpl bankAccountRepository;
//
//    @BeforeAll
//    public static void setUp() {
//        bankAccountRepository = new BankAccountRepositoryImpl();
//        // Add test data to the database
//        BankAccount bankAccount = new BankAccount();
//        bankAccount.setName("AA");
//        bankAccount.setAmount(1000);
//
//        BankAccount bankAccount2 = new BankAccount();
//        bankAccount2.setName("BB");
//        bankAccount2.setAmount(2000);
//
//        bankAccountRepository.save(bankAccount);
//        bankAccountRepository.save(bankAccount2);
//    }
//
//    @Test
//    public void testRemove() {
//        // Get the ID of the first bank account in the database
//        int id = bankAccountRepository.findAll().get(0).getId();
//
//        // Remove the bank account
//        boolean result = bankAccountRepository.remove(id);
//
//        // Check that the bank account was removed successfully
//        assertTrue(result);
//        assertNull(bankAccountRepository.findById(id));
//    }
//
//    @AfterAll
//    public static void tearDown() {
//        // Remove all test data from the database
//        for (BankAccount bankAccount : bankAccountRepository.findAll()) {
//            bankAccountRepository.remove(bankAccount.getId());
//        }
//    }
}
