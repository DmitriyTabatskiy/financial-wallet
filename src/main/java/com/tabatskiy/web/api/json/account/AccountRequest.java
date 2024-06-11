package com.tabatskiy.web.api.json.account;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.validation.constraints.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountRequest {

    @NonNull
    @Positive
    private Integer id;

    @NotEmpty
    @Size(min = 3, max = 20)
    private String name;

    @NonNull
    @Min(0)
    @Max(Integer.MAX_VALUE)
    private Integer balance;

    @NonNull
    @Positive
    private Integer clientId;
}