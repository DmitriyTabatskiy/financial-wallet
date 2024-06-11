package com.tabatskiy.web.config;

import org.springframework.context.annotation.Bean;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                    .authorizeRequests()
                        .antMatchers("/login-form", "/regist").permitAll()
                        .antMatchers("/api/registration").permitAll()
                        .anyRequest().authenticated()
                .and()
                    .formLogin()
                         .usernameParameter("email")
                         .passwordParameter("password")
                         .loginPage("/login-form")
                         .loginProcessingUrl("/login")
                         .defaultSuccessUrl("/personal-area")
                .and()
                    .logout()
                         .logoutUrl("/logout")
                         .logoutSuccessUrl("/login-form");
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}