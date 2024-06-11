package com.tabatskiy.web.service;

import com.tabatskiy.web.converter.AccountModelToAccountDtoConverter;
import com.tabatskiy.web.entity.Account;
import com.tabatskiy.web.entity.Client;
import com.tabatskiy.web.exception.CustomException;
import com.tabatskiy.web.repository.AccountRepository;
import com.tabatskiy.web.repository.ClientRepository;
import com.tabatskiy.web.service.AccountDTO;
import com.tabatskiy.web.service.AccountService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.Silent.class)
public class AccountServiceTest {

    @Mock
    AccountRepository accountRepository;

    @Mock
    ClientRepository clientRepository;

    @Mock
    AccountModelToAccountDtoConverter accountDtoConverter;

    @InjectMocks
    AccountService subj;

    @Test
    public void findAllByClientId_returnListAccountNotEmpty() {
        Account account = new Account();
        account.setId(1);
        account.setName("Alfa");
        account.setBalance(4700);
        account.setClient(account.getClient());
        List<Account> accounts = new ArrayList<>();
        accounts.add(account);

        when(accountRepository.findAllByClientId(1)).thenReturn(accounts);

        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setId(1);
        accountDTO.setName("Alfa");
        accountDTO.setBalance(4700);
        List<AccountDTO> accountDTOS = new ArrayList<>();
        accountDTOS.add(accountDTO);

        when(accountDtoConverter.convert(account)).thenReturn(accountDTO);

        List<AccountDTO> accountDTOList = subj.findAllByClientId(1);
        assertEquals(accountDTOS, accountDTOList);

        verify(accountRepository, times(1)).findAllByClientId(1);
        verify(accountDtoConverter, times(1)).convert(account);
    }

    @Test
    public void findAllByClientId_returnListAccountIsEmpty() {
        List<Account> accounts = new ArrayList<>();
        when(accountRepository.findAllByClientId(1)).thenReturn(accounts);

        List<AccountDTO> accountDTOS = new ArrayList<>();

        List<AccountDTO> accountDTOList = subj.findAllByClientId(1);

        assertEquals(accountDTOS, accountDTOList);

        verify(accountRepository, times(1)).findAllByClientId(1);
        verifyZeroInteractions(accountDtoConverter);
    }

    @Test
    public void delete_accountDeleteIsTrue() {
        Client client = new Client();
        client.setId(1);
        client.setEmail("email");
        client.setPassword("password");

        Account account = new Account();
        account.setId(1);
        account.setName("Alfa");
        account.setBalance(4700);
        account.setClient(client);
        when(accountRepository.findByIdAndClientId(1, client.getId())).thenReturn(Optional.of(account));

        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setId(1);
        accountDTO.setName("Alfa");
        accountDTO.setBalance(4700);
        when(accountDtoConverter.convert(account)).thenReturn(accountDTO);

        subj.delete(1, client.getId());

        verify(accountRepository, times(1)).findByIdAndClientId(1, client.getId());
        verify(accountRepository, times(1)).deleteAccountByIdAndClientId(1, client.getId());
        verify(accountDtoConverter, times(1)).convert(account);
    }

    @Test(expected = CustomException.class)
    public void delete_accountDeleteIsFalse() {
        Client client = new Client();
        client.setId(1);
        client.setEmail("email");
        client.setPassword("password");

        Account account = new Account();
        account.setId(1);
        account.setName("Alfa");
        account.setBalance(4700);
        account.setClient(client);
        accountRepository.delete(account);

        subj.delete(1, client.getId());

        verify(accountRepository, times(1)).delete(account);
        verifyZeroInteractions(accountDtoConverter);
    }

    @Test
    public void insert_accountInsert() {
        Client client = new Client();
        client.setId(1);
        client.setEmail("email");
        client.setPassword("password");
        when(clientRepository.findById(1)).thenReturn(Optional.of(client));

        Account account = new Account();
        account.setName("Alfa");
        account.setBalance(4700);
        account.setClient(client);
        when(accountRepository.save(account)).thenReturn(account);

        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setId(1);
        accountDTO.setName("Alfa");
        accountDTO.setBalance(4700);
        when(accountDtoConverter.convert(account)).thenReturn(accountDTO);

        AccountDTO accountDTO1 = subj.insert("Alfa", 4700, 1);
        assertNotNull(accountDTO1);
        assertEquals(accountDTO, accountDTO1);

        verify(clientRepository, times(1)).findById(1);
        verify(accountRepository, times(1)).save(account);
        verify(accountDtoConverter, times(1)).convert(account);
    }

    @Test(expected = CustomException.class)
    public void insert_accountNotInsert() {
        Account account = new Account();
        account.setName("Alfa");
        account.setBalance(4700);

        when(accountRepository.save(account)).thenReturn(null);

        subj.insert("Alfa", 4700, 1);

        verifyZeroInteractions(accountRepository);
        verifyZeroInteractions(accountDtoConverter);
    }
}