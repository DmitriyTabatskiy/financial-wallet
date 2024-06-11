package com.tabatskiy.web.web.form;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Data
public class ClientForm {

    @Email
    @NotEmpty
    private String email;

    @NotEmpty
    private String password;
}