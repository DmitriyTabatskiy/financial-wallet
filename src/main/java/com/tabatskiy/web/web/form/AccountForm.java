package com.tabatskiy.web.web.form;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;

import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountForm {

    @NonNull
    private Integer id;

    @NotEmpty
    private String name;

    @NotEmpty
    private Integer balance;
}