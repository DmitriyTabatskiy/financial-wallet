package com.tabatskiy.web.service;

import com.tabatskiy.web.converter.TransactionModelToTransactionDtoToFromAccountConverter;
import com.tabatskiy.web.converter.TransactionModelToTransactionDtoToFromAccountToAccountConverter;
import com.tabatskiy.web.converter.TransactionModelToTransactionDtoToToAccountConverter;
import com.tabatskiy.web.entity.Account;
import com.tabatskiy.web.entity.Category;
import com.tabatskiy.web.entity.Client;
import com.tabatskiy.web.entity.Transaction;
import com.tabatskiy.web.repository.AccountRepository;
import com.tabatskiy.web.repository.CategoryRepository;
import com.tabatskiy.web.repository.ClientRepository;
import com.tabatskiy.web.repository.TransactionalRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.Silent.class)
public class TransactionServiceTest {

    @Mock
    TransactionalRepository transactionalRepository;

    @Mock
    AccountRepository accountRepository;

    @Mock
    CategoryRepository categoryRepository;

    @Mock
    ClientRepository clientRepository;

    @Mock
    TransactionModelToTransactionDtoToFromAccountConverter transactionDtoToFromAccountConverter;

    @Mock
    TransactionModelToTransactionDtoToToAccountConverter transactionDtoToToAccountConverter;

    @Mock
    TransactionModelToTransactionDtoToFromAccountToAccountConverter transactionDtoToFromAccountToAccountConverter;

    @InjectMocks
    TransactionService subj;

    @Test
    public void insertFromAccount_isInsert() {
        LocalDateTime localDateTime = LocalDateTime.of(2023, 1, 3, 18, 56, 0, 712);

        Client client = new Client();
        client.setId(1);
        client.setEmail("client");
        client.setPassword("password");
        when(clientRepository.findById(1)).thenReturn(Optional.of(client));

        Account findAccount = new Account();
        findAccount.setId(1);
        findAccount.setName("account");
        findAccount.setBalance(3000);
        findAccount.setClient(client);
        when(accountRepository.findByIdAndClientId(1, client.getId())).thenReturn(Optional.of(findAccount));

        Account account = new Account();
        account.setId(findAccount.getId());
        account.setName(findAccount.getName());
        account.setBalance(findAccount.getBalance() - 2000);
        account.setClient(client);
        when(accountRepository.saveAndFlush(findAccount)).thenReturn(findAccount);

        Category category = new Category();
        category.setId(1);
        category.setName("category");
        category.setClient(client);
        when(categoryRepository.findByIdAndClientId(1, 1)).thenReturn(Optional.of(category));

        List<Category> categoryList = new ArrayList<>();
        categoryList.add(category);

        List<Integer> integers = new ArrayList<>();
        integers.add(1);

        Transaction transaction = new Transaction();
        transaction.setAccountFrom(findAccount);
        transaction.setAmount(2000);
        transaction.setCategories(categoryList);
        transaction.setCreatedDate(LocalDateTime.of(2023, 1, 3, 18, 56, 0, 712));
        when(transactionalRepository.saveAndFlush(transaction)).thenReturn(transaction);

        TransactionDTO transactionDTO = new TransactionDTO();
        transactionDTO.setId(1);
        transactionDTO.setFromAccountId(1);
        transactionDTO.setAmount(2000);
        transactionDTO.setCreatedDate(LocalDateTime.of(2023, 1, 3, 18, 56, 0, 712));
        when(transactionDtoToFromAccountConverter.convert(transaction)).thenReturn(transactionDTO);

        doAnswer(invocationOnMock -> {
            ReflectionTestUtils.setField((Transaction) invocationOnMock.getArgument(0), "createdDate", localDateTime);
            return any();
        }).when(transactionalRepository).saveAndFlush(any());

        TransactionDTO transactionResult = subj.insertFromAccount(1, 2000, 1, integers);

        assertNotNull(transactionResult);
        assertEquals(transactionDTO, transactionResult);

        verify(clientRepository, times(1)).findById(1);
        verify(accountRepository, times(1)).findByIdAndClientId(1, 1);
        verify(accountRepository, times(1)).saveAndFlush(account);
        verify(categoryRepository, times(1)).findByIdAndClientId(1, 1);
        verify(transactionalRepository, times(1)).saveAndFlush(transaction);
        verify(transactionDtoToFromAccountConverter, times(1)).convert(transaction);
    }

    @Test(expected = NullPointerException.class)
    public void insertFromAccount_isNotInsertNotFindAccount() {
        LocalDateTime localDateTime = LocalDateTime.of(2023, 1, 3, 18, 56, 0, 712);

        Client client = new Client();
        client.setId(1);
        client.setEmail("client");
        client.setPassword("password");
        when(clientRepository.findById(1)).thenReturn(Optional.of(client));

        Account findAccount = new Account();
        findAccount.setId(1);
        findAccount.setName("account");
        findAccount.setBalance(3000);
        findAccount.setClient(client);
        when(accountRepository.findByIdAndClientId(1, client.getId())).thenReturn(null);

        Account account = new Account();
        account.setId(findAccount.getId());
        account.setName(findAccount.getName());
        account.setBalance(findAccount.getBalance() - 2000);
        account.setClient(client);
        when(accountRepository.saveAndFlush(findAccount)).thenReturn(findAccount);

        Category category = new Category();
        category.setId(1);
        category.setName("category");
        category.setClient(client);
        when(categoryRepository.findByIdAndClientId(1, 1)).thenReturn(Optional.of(category));

        List<Category> categoryList = new ArrayList<>();
        categoryList.add(category);

        List<Integer> integers = new ArrayList<>();
        integers.add(1);

        Transaction transaction = new Transaction();
        transaction.setAccountFrom(findAccount);
        transaction.setAmount(2000);
        transaction.setCategories(categoryList);
        transaction.setCreatedDate(LocalDateTime.of(2023, 1, 3, 18, 56, 0, 712));
        when(transactionalRepository.saveAndFlush(transaction)).thenReturn(transaction);

        TransactionDTO transactionDTO = new TransactionDTO();
        transactionDTO.setId(1);
        transactionDTO.setFromAccountId(1);
        transactionDTO.setAmount(2000);
        transactionDTO.setCreatedDate(LocalDateTime.of(2023, 1, 3, 18, 56, 0, 712));
        when(transactionDtoToFromAccountConverter.convert(transaction)).thenReturn(transactionDTO);

        doAnswer(invocationOnMock -> {
            ReflectionTestUtils.setField((Transaction) invocationOnMock.getArgument(0), "createdDate", localDateTime);
            return any();
        }).when(transactionalRepository).saveAndFlush(any());

        TransactionDTO transactionResult = subj.insertFromAccount(1, 2000, 1, integers);

        assertNull(transactionResult);

        verify(clientRepository, times(1)).findById(1);
        verify(accountRepository, times(1)).findByIdAndClientId(1, 1);
        verifyZeroInteractions(accountRepository);
        verifyZeroInteractions(categoryRepository);
        verifyZeroInteractions(transactionalRepository);
        verifyZeroInteractions(transactionDtoToToAccountConverter);
    }

    @Test
    public void insertToAccount_isInsert() {
        LocalDateTime localDateTime = LocalDateTime.of(2023, 1, 3, 18, 56, 0, 712);

        Client client = new Client();
        client.setId(1);
        client.setEmail("client");
        client.setPassword("password");
        when(clientRepository.findById(1)).thenReturn(Optional.of(client));

        Account findAccount = new Account();
        findAccount.setId(1);
        findAccount.setName("account");
        findAccount.setBalance(3000);
        findAccount.setClient(client);
        when(accountRepository.findByIdAndClientId(1, client.getId())).thenReturn(Optional.of(findAccount));

        Account account = new Account();
        account.setId(findAccount.getId());
        account.setName(findAccount.getName());
        account.setBalance(findAccount.getBalance() + 2000);
        account.setClient(client);
        when(accountRepository.saveAndFlush(findAccount)).thenReturn(findAccount);

        Category category = new Category();
        category.setId(1);
        category.setName("category");
        category.setClient(client);
        when(categoryRepository.findByIdAndClientId(1, 1)).thenReturn(Optional.of(category));

        List<Category> categoryList = new ArrayList<>();
        categoryList.add(category);

        List<Integer> integers = new ArrayList<>();
        integers.add(1);

        Transaction transaction = new Transaction();
        transaction.setAccountTo(findAccount);
        transaction.setAmount(2000);
        transaction.setCategories(categoryList);
        transaction.setCreatedDate(LocalDateTime.of(2023, 1, 3, 18, 56, 0, 712));
        when(transactionalRepository.saveAndFlush(transaction)).thenReturn(transaction);

        TransactionDTO transactionDTO = new TransactionDTO();
        transactionDTO.setId(1);
        transactionDTO.setToAccountId(1);
        transactionDTO.setAmount(2000);
        transactionDTO.setCreatedDate(LocalDateTime.of(2023, 1, 3, 18, 56, 0, 712));
        when(transactionDtoToToAccountConverter.convert(transaction)).thenReturn(transactionDTO);

        doAnswer(invocationOnMock -> {
            ReflectionTestUtils.setField((Transaction) invocationOnMock.getArgument(0), "createdDate", localDateTime);
            return any();
        }).when(transactionalRepository).saveAndFlush(any());

        TransactionDTO transactionResult = subj.insertToAccount(1, 2000, 1, integers);

        assertNotNull(transactionResult);
        assertEquals(transactionDTO, transactionResult);

        verify(clientRepository, times(1)).findById(1);
        verify(accountRepository, times(1)).findByIdAndClientId(1, 1);
        verify(accountRepository, times(1)).saveAndFlush(account);
        verify(categoryRepository, times(1)).findByIdAndClientId(1, 1);
        verify(transactionalRepository, times(1)).saveAndFlush(transaction);
        verify(transactionDtoToToAccountConverter, times(1)).convert(transaction);
    }

    @Test(expected = AssertionError.class)
    public void insertToAccount_isNotInsertNotFindCategories() {
        LocalDateTime localDateTime = LocalDateTime.of(2023, 1, 3, 18, 56, 0, 712);

        Client client = new Client();
        client.setId(1);
        client.setEmail("client");
        client.setPassword("password");
        when(clientRepository.findById(1)).thenReturn(Optional.of(client));

        Account findAccount = new Account();
        findAccount.setId(1);
        findAccount.setName("account");
        findAccount.setBalance(3000);
        findAccount.setClient(client);
        when(accountRepository.findByIdAndClientId(1, client.getId())).thenReturn(Optional.of(findAccount));

        Account account = new Account();
        account.setId(findAccount.getId());
        account.setName(findAccount.getName());
        account.setBalance(findAccount.getBalance() + 2000);
        account.setClient(client);
        when(accountRepository.saveAndFlush(findAccount)).thenReturn(findAccount);

        Category category = new Category();
        category.setId(1);
        category.setName("category");
        category.setClient(client);
        when(categoryRepository.findByIdAndClientId(1, 1)).thenReturn(Optional.of(category));

        List<Category> categoryList = new ArrayList<>();

        List<Integer> integers = new ArrayList<>();
        integers.add(1);

        Transaction transaction = new Transaction();
        transaction.setAccountTo(findAccount);
        transaction.setAmount(2000);
        transaction.setCategories(categoryList);
        transaction.setCreatedDate(LocalDateTime.of(2023, 1, 3, 18, 56, 0, 712));
        when(transactionalRepository.saveAndFlush(transaction)).thenReturn(transaction);

        TransactionDTO transactionDTO = new TransactionDTO();
        transactionDTO.setId(1);
        transactionDTO.setToAccountId(1);
        transactionDTO.setAmount(2000);
        transactionDTO.setCreatedDate(LocalDateTime.of(2023, 1, 3, 18, 56, 0, 712));
        when(transactionDtoToToAccountConverter.convert(transaction)).thenReturn(transactionDTO);

        doAnswer(invocationOnMock -> {
            ReflectionTestUtils.setField((Transaction) invocationOnMock.getArgument(0), "createdDate", localDateTime);
            return any();
        }).when(transactionalRepository).saveAndFlush(any());

        TransactionDTO transactionResult = subj.insertToAccount(1, 2000, 1, integers);

        assertNotNull(transactionResult);
        assertEquals(transactionDTO, transactionResult);

        verify(clientRepository, times(1)).findById(1);
        verify(accountRepository, times(1)).findByIdAndClientId(1, 1);
        verify(accountRepository, times(1)).saveAndFlush(account);
        verify(categoryRepository, times(1)).findByIdAndClientId(1, 1);
        verify(transactionalRepository, times(1)).saveAndFlush(transaction);
        verify(transactionDtoToToAccountConverter, times(1)).convert(transaction);
    }

    @Test
    public void insertAccountToAccount_isInsert() {
        LocalDateTime localDateTime = LocalDateTime.of(2023, 1, 3, 18, 56, 0, 712);

        Client client = new Client();
        client.setId(1);
        client.setEmail("client");
        client.setPassword("password");
        when(clientRepository.findById(1)).thenReturn(Optional.of(client));

        Account findAccountFrom = new Account();
        findAccountFrom.setId(1);
        findAccountFrom.setName("account");
        findAccountFrom.setBalance(3000);
        findAccountFrom.setClient(client);
        when(accountRepository.findByIdAndClientId(1, client.getId())).thenReturn(Optional.of(findAccountFrom));

        Account accountFrom = new Account();
        accountFrom.setId(findAccountFrom.getId());
        accountFrom.setName(findAccountFrom.getName());
        accountFrom.setBalance(findAccountFrom.getBalance() - 2000);
        accountFrom.setClient(client);
        when(accountRepository.saveAndFlush(findAccountFrom)).thenReturn(findAccountFrom);

        Account findAccountTo = new Account();
        findAccountTo.setId(2);
        findAccountTo.setName("account");
        findAccountTo.setBalance(3000);
        findAccountTo.setClient(client);
        when(accountRepository.findByIdAndClientId(2, client.getId())).thenReturn(Optional.of(findAccountTo));

        Account accountTo = new Account();
        accountTo.setId(findAccountTo.getId());
        accountTo.setName(findAccountTo.getName());
        accountTo.setBalance(findAccountTo.getBalance() + 2000);
        accountTo.setClient(client);
        when(accountRepository.saveAndFlush(findAccountTo)).thenReturn(findAccountTo);

        Category category = new Category();
        category.setId(1);
        category.setName("category");
        category.setClient(client);
        when(categoryRepository.findByIdAndClientId(1, 1)).thenReturn(Optional.of(category));

        List<Category> categoryList = new ArrayList<>();
        categoryList.add(category);

        List<Integer> integers = new ArrayList<>();
        integers.add(1);

        Transaction transaction = new Transaction();
        transaction.setAccountFrom(findAccountFrom);
        transaction.setAccountTo(findAccountTo);
        transaction.setAmount(2000);
        transaction.setCategories(categoryList);
        transaction.setCreatedDate(LocalDateTime.of(2023, 1, 3, 18, 56, 0, 712));
        when(transactionalRepository.saveAndFlush(transaction)).thenReturn(transaction);

        TransactionDTO transactionDTO = new TransactionDTO();
        transactionDTO.setId(1);
        transactionDTO.setFromAccountId(1);
        transactionDTO.setAmount(2000);
        transactionDTO.setCreatedDate(LocalDateTime.of(2023, 1, 3, 18, 56, 0, 712));
        when(transactionDtoToFromAccountToAccountConverter.convert(transaction)).thenReturn(transactionDTO);

        doAnswer(invocationOnMock -> {
            ReflectionTestUtils.setField((Transaction) invocationOnMock.getArgument(0), "createdDate", localDateTime);
            return any();
        }).when(transactionalRepository).saveAndFlush(any());

        TransactionDTO transactionResult = subj.insertAccountToAccount(1, 2, 2000, 1, integers);

        assertNotNull(transactionResult);
        assertEquals(transactionDTO, transactionResult);

        verify(clientRepository, times(2)).findById(1);
        verify(accountRepository, times(1)).findByIdAndClientId(1, 1);
        verify(accountRepository, times(1)).saveAndFlush(accountFrom);
        verify(accountRepository, times(1)).findByIdAndClientId(2, 1);
        verify(accountRepository, times(1)).saveAndFlush(accountTo);
        verify(categoryRepository, times(1)).findByIdAndClientId(1, 1);
        verify(transactionalRepository, times(1)).saveAndFlush(transaction);
        verify(transactionDtoToFromAccountToAccountConverter, times(1)).convert(transaction);
    }

    @Test(expected = AssertionError.class)
    public void insertAccountToAccount_isNotInsertNotSaveTransaction() {
        LocalDateTime localDateTime = LocalDateTime.of(2023, 1, 3, 18, 56, 0, 712);

        Client client = new Client();
        client.setId(1);
        client.setEmail("client");
        client.setPassword("password");
        when(clientRepository.findById(1)).thenReturn(Optional.of(client));

        Account findAccountFrom = new Account();
        findAccountFrom.setId(1);
        findAccountFrom.setName("account");
        findAccountFrom.setBalance(3000);
        findAccountFrom.setClient(client);
        when(accountRepository.findByIdAndClientId(1, client.getId())).thenReturn(Optional.of(findAccountFrom));

        Account accountFrom = new Account();
        accountFrom.setId(findAccountFrom.getId());
        accountFrom.setName(findAccountFrom.getName());
        accountFrom.setBalance(findAccountFrom.getBalance() - 2000);
        accountFrom.setClient(client);
        when(accountRepository.saveAndFlush(findAccountFrom)).thenReturn(findAccountFrom);

        Account findAccountTo = new Account();
        findAccountTo.setId(2);
        findAccountTo.setName("account");
        findAccountTo.setBalance(3000);
        findAccountTo.setClient(client);
        when(accountRepository.findByIdAndClientId(2, client.getId())).thenReturn(Optional.of(findAccountTo));

        Account accountTo = new Account();
        accountTo.setId(findAccountTo.getId());
        accountTo.setName(findAccountTo.getName());
        accountTo.setBalance(findAccountTo.getBalance() + 2000);
        accountTo.setClient(client);
        when(accountRepository.saveAndFlush(findAccountTo)).thenReturn(findAccountTo);

        Category category = new Category();
        category.setId(1);
        category.setName("category");
        category.setClient(client);
        when(categoryRepository.findByIdAndClientId(1, 1)).thenReturn(Optional.of(category));

        List<Category> categoryList = new ArrayList<>();
        categoryList.add(category);

        List<Integer> integers = new ArrayList<>();
        integers.add(1);

        Transaction transaction = new Transaction();
        transaction.setAccountFrom(findAccountFrom);
        transaction.setAccountTo(findAccountTo);
        transaction.setAmount(2000);
        transaction.setCategories(categoryList);
        transaction.setCreatedDate(LocalDateTime.of(2023, 1, 3, 18, 56, 0, 712));
        when(transactionalRepository.saveAndFlush(transaction)).thenReturn(null);

        doAnswer(invocationOnMock -> {
            ReflectionTestUtils.setField((Transaction) invocationOnMock.getArgument(0), "createdDate", localDateTime);
            return any();
        }).when(transactionalRepository).saveAndFlush(any());

        TransactionDTO transactionResult = subj.insertAccountToAccount(1, 2, 2000, 1, integers);

        assertNull(transactionResult);

        verify(clientRepository, times(2)).findById(1);
        verify(accountRepository, times(1)).findByIdAndClientId(1, 1);
        verify(accountRepository, times(1)).saveAndFlush(accountFrom);
        verify(accountRepository, times(1)).findByIdAndClientId(2, 1);
        verify(accountRepository, times(1)).saveAndFlush(accountTo);
        verify(categoryRepository, times(1)).findByIdAndClientId(1, 1);
        verify(transactionalRepository, times(1)).saveAndFlush(transaction);
        verifyZeroInteractions(transactionDtoToFromAccountToAccountConverter);
    }
}