package com.tabatskiy.web.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionDTO {
    private Integer id;
    private Integer fromAccountId;
    private Integer toAccountId;
    private Integer amount;
    private LocalDateTime createdDate;
}