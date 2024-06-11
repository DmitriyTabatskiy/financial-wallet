package com.tabatskiy.web.converter;

import com.tabatskiy.web.entity.Transaction;
import com.tabatskiy.web.service.TransactionDTO;
import org.springframework.stereotype.Service;

@Service
public class TransactionModelToTransactionDtoToFromAccountToAccountConverter implements Converter<Transaction, TransactionDTO> {

    @Override
    public TransactionDTO convert(Transaction source) {
        TransactionDTO transactionDTO = new TransactionDTO();
        transactionDTO.setId(source.getId());
        transactionDTO.setFromAccountId(source.getAccountFrom().getId());
        transactionDTO.setToAccountId(source.getAccountTo().getId());
        transactionDTO.setAmount(source.getAmount());
        transactionDTO.setCreatedDate(source.getCreatedDate());
        return transactionDTO;
    }
}