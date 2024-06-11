package com.tabatskiy.web.api.converter;

import com.tabatskiy.web.api.json.transaction.TransactionResponse;
import com.tabatskiy.web.service.TransactionDTO;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class TransactionToResponseConverter implements Converter<TransactionDTO, TransactionResponse> {

    @Override
    public TransactionResponse convert(TransactionDTO transactionDTO) {
        return new TransactionResponse(
                transactionDTO.getId(),
                transactionDTO.getFromAccountId(),
                transactionDTO.getToAccountId(),
                transactionDTO.getAmount(),
                transactionDTO.getCreatedDate());
    }
}