package com.tabatskiy.web.api.controller;

import com.tabatskiy.web.api.converter.AccountToResponseConverter;
import com.tabatskiy.web.api.json.account.AccountRequestDelete;
import com.tabatskiy.web.api.json.account.AccountResponse;
import com.tabatskiy.web.api.json.account.AccountRequest;
import com.tabatskiy.web.service.AccountDTO;
import com.tabatskiy.web.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.tabatskiy.web.service.ClientService.currentClientId;
import static java.util.stream.Collectors.toList;
import static org.springframework.http.ResponseEntity.ok;
import static org.springframework.http.ResponseEntity.status;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class AccountController {

    private final AccountService accountService;
    private final AccountToResponseConverter converter;

    @GetMapping("/accounts")
    public ResponseEntity<List<AccountResponse>> accounts() {
        Integer clientId = currentClientId();

        List<AccountDTO> accountDTOS = accountService.findAllByClientId(clientId);

        if (accountDTOS.isEmpty()) {
            return status(HttpStatus.NO_CONTENT).build();
        }

        List<AccountResponse> accounts = accountService.findAllByClientId(clientId).stream()
                .map(converter::convert)
                .collect(toList());
        return ok(accounts);
    }

    @DeleteMapping("/delete-account")
    public ResponseEntity<AccountResponse> deleteAccount(@RequestBody @Validated AccountRequestDelete accountRequest) {
        AccountDTO accountDTO = accountService.delete(accountRequest.getId(), accountRequest.getClientId());

        if (accountDTO == null) {
            return status(HttpStatus.NO_CONTENT).build();
        }

        return ok(converter.convert(accountDTO));

    }

    @PostMapping("/insert-account")
    public ResponseEntity<AccountResponse> insertAccount(@RequestBody @Validated AccountRequest accountRequest) {
        Integer clientId = currentClientId();

        AccountDTO accountDTO = accountService.insert(
                accountRequest.getName(), accountRequest.getBalance(), clientId);

        return ok(converter.convert(accountDTO));
    }
}