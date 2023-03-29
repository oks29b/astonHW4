package repositoryTests;

import org.example.model.entity.BankAccount;
import org.example.model.repository.impl.BankAccountRepositoryImpl;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class BankAccountRepositoryTest {
    @Mock
    private static BankAccountRepositoryImpl bankAccountRepository;

    @Test
    void findAllReturnValidBankAccount(){
        List<BankAccount> bankAccountList = List.of(
                mock(BankAccount.class),
                mock(BankAccount.class),
                mock(BankAccount.class)
        );

        when(bankAccountRepository.findAll()).thenReturn(bankAccountList);

        List<BankAccount> testList = bankAccountRepository.findAll();

        assertNotNull(testList);
        assertFalse(testList.isEmpty());
        assertEquals(3, testList.size());
        verify(bankAccountRepository).findAll();
    }
}
