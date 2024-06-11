package com.tabatskiy.web.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.tabatskiy.web.MockSecurityConfiguration;
import com.tabatskiy.web.api.converter.AccountToResponseConverter;
import com.tabatskiy.web.api.json.account.AccountRequest;
import com.tabatskiy.web.api.json.account.AccountRequestDelete;
import com.tabatskiy.web.config.SecurityConfiguration;

import com.tabatskiy.web.service.AccountDTO;
import com.tabatskiy.web.service.AccountService;
import com.tabatskiy.web.service.ClientDTO;
import com.tabatskiy.web.service.ClientService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static java.util.Collections.singletonList;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AccountController.class)
@Import({SecurityConfiguration.class, MockSecurityConfiguration.class})
@RunWith(SpringRunner.class)
public class AccountControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    AccountService accountService;

    @MockBean
    ClientService clientService;

    @SpyBean
    AccountToResponseConverter converter;

    @Before
    public void setUp() {
        when(clientService.currentClient()).thenReturn(
                new ClientDTO(1,
                        "yana@gmail.com"))
        ;
    }

    @WithUserDetails(value = "yana@gmail.com", userDetailsServiceBeanName = "userDetailsService")
    @Test
    public void accounts_whenGetAccounts_thenStatus200() throws Exception {
        AccountDTO accountDTO = new AccountDTO(1, "account", 2000);

        when(accountService.findAllByClientId(1)).thenReturn(singletonList(accountDTO));

        mockMvc.perform(get("/api/accounts"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().json(objectMapper.writeValueAsString(singletonList(accountDTO))));
    }

    @WithUserDetails(value = "yana@gmail.com", userDetailsServiceBeanName = "userDetailsService")
    @Test
    public void deleteAccount_whenDeleteAccount_thenStatus200() throws Exception {
        AccountDTO accountDTO = new AccountDTO(1, "account", 2000);

        when(accountService.delete(1, 1)).thenReturn(accountDTO);

        ObjectWriter objectWriter = objectMapper.writer();

        mockMvc.perform(delete("/api/delete-account")
                        .contentType(APPLICATION_JSON)
                        .content(objectWriter.writeValueAsString(new AccountRequestDelete(1, 1))))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(accountDTO)));
    }

    @WithUserDetails(value = "yana@gmail.com", userDetailsServiceBeanName = "userDetailsService")
    @Test
    public void insertAccount_whenInsert_thenStatus200() throws Exception {
        AccountDTO accountDTO = new AccountDTO(1, "account", 2000);

        when(accountService.insert("account", 2000, 1)).thenReturn(accountDTO);

        ObjectWriter objectWriter = objectMapper.writer();

        mockMvc.perform(post("/api/insert-account")
                        .contentType(APPLICATION_JSON)
                        .content(objectWriter.writeValueAsString(new AccountRequest(1, "account", 2000, 1))))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(accountDTO)));
    }
}