package com.tabatskiy.web.api.controller;

import com.tabatskiy.web.api.converter.ClientToResponseConverter;
import com.tabatskiy.web.api.json.client.ClientRequest;
import com.tabatskiy.web.api.json.client.ClientResponse;
import com.tabatskiy.web.security.CustomUserDetails;
import com.tabatskiy.web.service.ClientDTO;
import com.tabatskiy.web.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ClientController {

    private final ClientService clientService;
    private final ClientToResponseConverter converter;

    @PostMapping("/registration")
    public ResponseEntity<ClientResponse> registration(@RequestBody @Validated ClientRequest request) {
        ClientDTO client = clientService.registration(
                request.getEmail(),
                request.getPassword()
        );

        return ok(converter.convert(client));
    }
}