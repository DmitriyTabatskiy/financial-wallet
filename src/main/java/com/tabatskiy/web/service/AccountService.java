package com.tabatskiy.web.service;

import com.tabatskiy.web.converter.AccountModelToAccountDtoConverter;
import com.tabatskiy.web.entity.Account;
import com.tabatskiy.web.entity.Client;
import com.tabatskiy.web.exception.CustomException;
import com.tabatskiy.web.repository.AccountRepository;
import com.tabatskiy.web.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AccountService {

    private final AccountRepository accountRepository;
    private final ClientRepository clientRepository;
    private final AccountModelToAccountDtoConverter accountDtoConverter;

    public List<AccountDTO> findAllByClientId(Integer clientId) {
        return accountRepository.findAllByClientId(clientId).stream()
                .map(accountDtoConverter::convert)
                .collect(toList());
    }

    @Transactional
    public AccountDTO delete(Integer accountId, Integer clientId) {
        Account account = accountRepository.findByIdAndClientId(accountId, clientId)
                .orElseThrow(() -> new CustomException("Account by id " + accountId + " was not found"));

        accountRepository.deleteAccountByIdAndClientId(accountId, clientId);
        return accountDtoConverter.convert(account);
    }

    @Transactional
    public AccountDTO insert(String nameAccount, Integer balance, Integer clientId) {
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new CustomException("Client by id " + clientId + " was not found"));

        Account account = new Account();
        account.setName(nameAccount);
        account.setBalance(balance);
        account.setClient(client);
        accountRepository.save(account);
        return accountDtoConverter.convert(account);
    }
}