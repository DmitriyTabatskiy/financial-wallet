package com.tabatskiy.web.api.json.client;

import lombok.Data;
import org.springframework.lang.NonNull;
import javax.validation.constraints.Email;

@Data
public class ClientRequest {

    @Email
    @NonNull
    private String email;

    @NonNull
    private String password;
}