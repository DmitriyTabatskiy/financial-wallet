package com.tabatskiy.web.service;

import com.tabatskiy.web.converter.ClientModelToClientDtoConverter;
import com.tabatskiy.web.entity.Client;
import com.tabatskiy.web.exception.CustomException;
import com.tabatskiy.web.repository.ClientRepository;
import com.tabatskiy.web.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ClientService {

    private final ClientRepository clientRepository;
    private final PasswordEncoder passwordEncoder;
    private final ClientModelToClientDtoConverter clientDtoConverter;

    public ClientDTO auth(String email, String password) {
        String passwordHash = passwordEncoder.encode(password);
        Client client = clientRepository.findByEmailAndPassword(email, passwordHash)
                .orElseThrow(() -> new CustomException("Client by email " + email + " was not found"));

        return clientDtoConverter.convert(client);
    }

    @Transactional
    public ClientDTO registration(String email, String password) {
        String passwordHash = passwordEncoder.encode(password);
        Client client = new Client();
        client.setEmail(email);
        client.setPassword(passwordHash);

        try {
            clientRepository.save(client);
        } catch (RuntimeException e) {
            throw new CustomException("This email \"" + email + "\" is already in use, please choose another one");
        }

        return clientDtoConverter.convert(client);
    }

    public ClientDTO currentClient() {
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();

        Client client = clientRepository.findById(userDetails.getId())
                .orElseThrow(() -> new CustomException("Client was not found"));

        return clientDtoConverter.convert(client);
    }

    public static Integer currentClientId() {
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();

        return userDetails.getId();
    }
}