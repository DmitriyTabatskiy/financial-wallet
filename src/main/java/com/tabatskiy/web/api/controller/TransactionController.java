package com.tabatskiy.web.api.controller;

import com.tabatskiy.web.api.converter.TransactionToResponseConverter;
import com.tabatskiy.web.api.json.transaction.TransactionRequest;
import com.tabatskiy.web.api.json.transaction.TransactionResponse;
import com.tabatskiy.web.service.TransactionDTO;
import com.tabatskiy.web.service.TransactionService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static com.tabatskiy.web.service.ClientService.currentClientId;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class TransactionController {

    private final TransactionService transactionService;
    private final TransactionToResponseConverter converter;

    @PutMapping("/transaction-insert-from-account")
    public ResponseEntity<TransactionResponse> transactionInsertFromAccount(@RequestBody @Validated TransactionRequest transactionRequest) {
        Integer clientId = currentClientId();

        TransactionDTO transactionDTO = transactionService.insertFromAccount(
                transactionRequest.getFromAccountId(),
                transactionRequest.getAmount(),
                clientId,
                transactionRequest.getIntegerList()
        );

        return ok(converter.convert(transactionDTO));
    }

    @PutMapping("/transaction-insert-to-account")
    public ResponseEntity<TransactionResponse> transactionInsertToAccount(@RequestBody @Validated TransactionRequest transactionRequest) {
        Integer clientId = currentClientId();

        TransactionDTO transactionDTO = transactionService.insertToAccount(
                transactionRequest.getToAccountId(),
                transactionRequest.getAmount(),
                clientId,
                transactionRequest.getIntegerList()
        );

        return ok(converter.convert(transactionDTO));
    }

    @PutMapping("/transaction-insert-account-to-account")
    public ResponseEntity<TransactionResponse> transactionInsertAccountToAccount(@RequestBody @Validated TransactionRequest transactionRequest) {
        Integer clientId = currentClientId();

        TransactionDTO transactionDTO = transactionService.insertAccountToAccount(
                transactionRequest.getFromAccountId(),
                transactionRequest.getToAccountId(),
                transactionRequest.getAmount(),
                clientId,
                transactionRequest.getIntegerList()
        );

        return ok(converter.convert(transactionDTO));
    }
}