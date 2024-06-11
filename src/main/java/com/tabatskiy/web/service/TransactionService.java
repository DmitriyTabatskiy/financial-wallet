package com.tabatskiy.web.service;

import com.tabatskiy.web.converter.TransactionModelToTransactionDtoToFromAccountConverter;
import com.tabatskiy.web.converter.TransactionModelToTransactionDtoToFromAccountToAccountConverter;
import com.tabatskiy.web.converter.TransactionModelToTransactionDtoToToAccountConverter;
import com.tabatskiy.web.entity.Account;
import com.tabatskiy.web.entity.Category;
import com.tabatskiy.web.entity.Client;
import com.tabatskiy.web.entity.Transaction;
import com.tabatskiy.web.exception.CustomException;
import com.tabatskiy.web.repository.AccountRepository;
import com.tabatskiy.web.repository.CategoryRepository;
import com.tabatskiy.web.repository.ClientRepository;
import com.tabatskiy.web.repository.TransactionalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionalRepository transactionalRepository;
    private final AccountRepository accountRepository;
    private final CategoryRepository categoryRepository;
    private final ClientRepository clientRepository;
    private final TransactionModelToTransactionDtoToFromAccountConverter transactionDtoToFromAccountConverter;
    private final TransactionModelToTransactionDtoToToAccountConverter modelToTransactionDtoToToAccountConverter;
    private final TransactionModelToTransactionDtoToFromAccountToAccountConverter toTransactionDtoToFromAccountToAccountConverter;

    @Transactional
    public TransactionDTO insertFromAccount(Integer fromAccountId, Integer amount, Integer clientId, List<Integer> integersId) {
        Account account = accountRepository.findByIdAndClientId(fromAccountId, clientId)
                .orElseThrow(() -> new CustomException("Account by id " + fromAccountId + " was not found"));
        account.setId(fromAccountId);
        account.setName(account.getName());
        account.setBalance(account.getBalance() - amount);
        account.setClient(findClient(clientId));
        accountRepository.saveAndFlush(account);

        Transaction transaction = new Transaction();
        transaction.setAccountFrom(account);
        transaction.setAmount(amount);
        transaction.setCreatedDate((LocalDateTime.now()));
        transaction.setCategories(categories(integersId, clientId));
        transactionalRepository.saveAndFlush(transaction);

        return transactionDtoToFromAccountConverter.convert(transaction);
    }

    @Transactional
    public TransactionDTO insertToAccount(Integer toAccountId, Integer amount, Integer clientId, List<Integer> integersId) {
        Account account = accountRepository.findByIdAndClientId(toAccountId, clientId)
                .orElseThrow(() -> new CustomException("Account by id " + toAccountId + " was not found"));
        account.setId(toAccountId);
        account.setName(account.getName());
        account.setBalance(account.getBalance() + amount);
        account.setClient(findClient(clientId));
        accountRepository.saveAndFlush(account);

        Transaction transaction = new Transaction();
        transaction.setAccountTo(account);
        transaction.setAmount(amount);
        transaction.setCreatedDate((LocalDateTime.now()));
        transaction.setCategories(categories(integersId, clientId));
        transactionalRepository.saveAndFlush(transaction);
        return modelToTransactionDtoToToAccountConverter.convert(transaction);
    }

    @Transactional
    public TransactionDTO insertAccountToAccount(Integer fromAccountId, Integer toAccountId, Integer amount, Integer clientId, List<Integer> integersId) {
        Account accountFrom = accountRepository.findByIdAndClientId(fromAccountId, clientId)
                .orElseThrow(() -> new CustomException("Account by id " + fromAccountId + " was not found"));
        accountFrom.setId(fromAccountId);
        accountFrom.setName(accountFrom.getName());
        accountFrom.setBalance(accountFrom.getBalance() - amount);
        accountFrom.setClient(findClient(clientId));
        accountRepository.saveAndFlush(accountFrom);

        Account accountTo = accountRepository.findByIdAndClientId(toAccountId, clientId)
                .orElseThrow(() -> new CustomException("Account by id " + toAccountId + " was not found"));
        accountTo.setId(toAccountId);
        accountTo.setName(accountTo.getName());
        accountTo.setBalance(accountTo.getBalance() + amount);
        accountTo.setClient(findClient(clientId));
        accountRepository.saveAndFlush(accountTo);

        Transaction transaction = new Transaction();
        transaction.setAccountFrom(accountFrom);
        transaction.setAccountTo(accountTo);
        transaction.setAmount(amount);
        transaction.setCreatedDate((LocalDateTime.now()));
        transaction.setCategories(categories(integersId, clientId));
        transactionalRepository.saveAndFlush(transaction);
        return toTransactionDtoToFromAccountToAccountConverter.convert(transaction);
    }

    private Client findClient(Integer clientId) {
        return clientRepository.findById(clientId)
                .orElseThrow(() -> new CustomException("Client by id " + clientId + " was not found"));
    }

    private List<Category> categories(List<Integer> categoryId, Integer clientId) {
        List<Category> categoryList = new ArrayList<>();
        for (Integer integer : categoryId) {
            categoryList.add(categoryRepository.findByIdAndClientId(integer, clientId)
                    .orElseThrow(() -> new CustomException("Category by id " + integer + " was not found")));
        }
        return categoryList;
    }
}