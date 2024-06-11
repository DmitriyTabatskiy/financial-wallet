package com.tabatskiy.web.api.json.transaction;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class TransactionResponse {
    private Integer id;
    private Integer fromAccountId;
    private Integer toAccountId;
    private Integer amount;
    private LocalDateTime createdDate;
}