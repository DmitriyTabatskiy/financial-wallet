package com.tabatskiy.web.api.json.account;

import lombok.Data;
import lombok.NonNull;

import javax.validation.constraints.Positive;

@Data
public class AccountRequestDelete {

    @NonNull
    @Positive
    private Integer id;

    @NonNull
    @Positive
    private Integer clientId;
}