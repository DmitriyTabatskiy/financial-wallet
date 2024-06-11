package com.tabatskiy.web;

import com.tabatskiy.web.security.CustomGrantedAuthority;
import com.tabatskiy.web.security.CustomUserDetails;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;

import static com.tabatskiy.web.security.UserRole.USER;

import static java.util.Collections.singleton;

@Configuration
public class MockSecurityConfiguration {

    @Bean
    public UserDetailsService userDetailsService() {
        return s -> new CustomUserDetails(
                1,
                "yana@gmail.com",
                "12345",
                singleton(new CustomGrantedAuthority(USER))
        );
    }
}