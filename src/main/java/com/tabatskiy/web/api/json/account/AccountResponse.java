package com.tabatskiy.web.api.json.account;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AccountResponse {
    private Integer id;
    private String name;
    private Integer balance;
}