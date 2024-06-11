package com.tabatskiy.web.api.json.transaction;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionRequest {

    @Positive
    private Integer fromAccountId;

    @Positive
    private Integer toAccountId;

    @NonNull
    @Min(0)
    @Max(Integer.MAX_VALUE)
    private Integer amount;

    @NonNull
    @Positive
    private Integer clientId;

    @NotEmpty
    private List<Integer> integerList;
}