package com.tabatskiy.web.converter;

import com.tabatskiy.web.entity.Account;
import com.tabatskiy.web.service.AccountDTO;
import org.springframework.stereotype.Service;

@Service
public class AccountModelToAccountDtoConverter implements Converter<Account, AccountDTO> {

    @Override
    public AccountDTO convert(Account source) {
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setId(source.getId());
        accountDTO.setName(source.getName());
        accountDTO.setBalance(source.getBalance());
        return accountDTO;
    }

}