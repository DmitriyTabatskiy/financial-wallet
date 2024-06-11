package com.tabatskiy.web.api.json.client;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ClientResponse {
    private Integer id;
    private String email;
}