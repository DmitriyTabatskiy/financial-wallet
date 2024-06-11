package com.tabatskiy.web.api.converter;

import com.tabatskiy.web.api.json.account.AccountResponse;
import com.tabatskiy.web.service.AccountDTO;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class AccountToResponseConverter implements Converter<AccountDTO, AccountResponse> {

    @Override
    public AccountResponse convert(AccountDTO accountDTO) {
        return new AccountResponse(accountDTO.getId(),accountDTO.getName(),accountDTO.getBalance());
    }
}