package com.tabatskiy.web.security;

import com.tabatskiy.web.entity.Client;
import com.tabatskiy.web.exception.CustomException;
import com.tabatskiy.web.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class CustomeUserDetailsService implements UserDetailsService {

    private final ClientRepository clientRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Client client = clientRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException("Client was not found"));

        return new CustomUserDetails(
                client.getId(),
                client.getEmail(),
                client.getPassword(),
                client.getRoles()
                        .stream()
                        .map(CustomGrantedAuthority::new)
                        .collect(toList())
        );
    }
}