package com.tabatskiy.web.web.form;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class TransactionForm {
    private Integer fromAccountId;
    private Integer toAccountId;

    @NotNull
    @Min(0)
    @Max(Integer.MAX_VALUE)
    private Integer amount;
    private Integer clientId;

    @NotEmpty
    private List<Integer> integerList;
}