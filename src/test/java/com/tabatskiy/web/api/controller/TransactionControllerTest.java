package com.tabatskiy.web.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.tabatskiy.web.MockSecurityConfiguration;

import com.tabatskiy.web.api.converter.TransactionToResponseConverter;
import com.tabatskiy.web.api.json.transaction.TransactionRequest;
import com.tabatskiy.web.config.SecurityConfiguration;
import com.tabatskiy.web.service.*;
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

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TransactionController.class)
@Import({SecurityConfiguration.class, MockSecurityConfiguration.class})
@RunWith(SpringRunner.class)
public class TransactionControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    TransactionService transactionService;

    @MockBean
    ClientService clientService;

    @SpyBean
    TransactionToResponseConverter converter;

    @Before
    public void setUp() {
        when(clientService.currentClient()).thenReturn(
                new ClientDTO(1,
                        "yana@gmail.com"))
        ;
    }

    @WithUserDetails(value = "yana@gmail.com", userDetailsServiceBeanName = "userDetailsService")
    @Test
    public void transactionInsertFromAccount_whenReturnFromAccount_thenStatus200() throws Exception {
        LocalDateTime localDateTime = LocalDateTime.of(2023, 5, 26, 13, 56);
        TransactionDTO transactionDTO = new TransactionDTO(1, 1, null, 2000, localDateTime);

        List<Integer> categoryList = new ArrayList<>();
        categoryList.add(1);

        when(transactionService.insertFromAccount(1, 2000, 1, categoryList)).thenReturn(transactionDTO);

        ObjectWriter objectWriter = objectMapper.writer();

        mockMvc.perform(put("/api/transaction-insert-from-account")
                        .contentType(APPLICATION_JSON)
                        .content(objectWriter.writeValueAsString(new TransactionRequest(1, null, 2000, 1, categoryList))))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(transactionDTO)));
    }

    @WithUserDetails(value = "yana@gmail.com", userDetailsServiceBeanName = "userDetailsService")
    @Test
    public void transactionInsertToAccount_whenReturnToAccount_thenStatus200() throws Exception {
        LocalDateTime localDateTime = LocalDateTime.of(2023, 5, 26, 13, 56);
        TransactionDTO transactionDTO = new TransactionDTO(1, null, 1, 2000, localDateTime);

        List<Integer> categoryList = new ArrayList<>();
        categoryList.add(1);

        when(transactionService.insertToAccount(1, 2000, 1, categoryList)).thenReturn(transactionDTO);

        ObjectWriter objectWriter = objectMapper.writer();

        mockMvc.perform(put("/api/transaction-insert-to-account")
                        .contentType(APPLICATION_JSON)
                        .content(objectWriter.writeValueAsString(new TransactionRequest(null, 1, 2000, 1, categoryList))))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(transactionDTO)));
    }

    @WithUserDetails(value = "yana@gmail.com", userDetailsServiceBeanName = "userDetailsService")
    @Test
    public void transactionInsertAccountToAccount_whenReturnFromAccountToAccount_thenStatus200() throws Exception {
        LocalDateTime localDateTime = LocalDateTime.of(2023, 5, 26, 13, 56);
        TransactionDTO transactionDTO = new TransactionDTO(1, 1, 2, 2000, localDateTime);

        List<Integer> categoryList = new ArrayList<>();
        categoryList.add(1);

        when(transactionService.insertAccountToAccount(1, 2, 2000, 1, categoryList)).thenReturn(transactionDTO);

        ObjectWriter objectWriter = objectMapper.writer();

        mockMvc.perform(put("/api/transaction-insert-account-to-account")
                        .contentType(APPLICATION_JSON)
                        .content(objectWriter.writeValueAsString(new TransactionRequest(1, 2, 2000, 1, categoryList))))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(transactionDTO)));
    }
}