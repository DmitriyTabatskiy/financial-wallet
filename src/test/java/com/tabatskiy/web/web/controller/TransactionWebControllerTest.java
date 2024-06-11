package com.tabatskiy.web.web.controller;

import com.tabatskiy.web.MockSecurityConfiguration;
import com.tabatskiy.web.config.SecurityConfiguration;
import com.tabatskiy.web.service.TransactionDTO;
import com.tabatskiy.web.service.TransactionService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TransactionWebController.class)
@Import({SecurityConfiguration.class, MockSecurityConfiguration.class})
@RunWith(SpringRunner.class)
public class TransactionWebControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    TransactionService transactionService;

    @WithUserDetails(value = "yana@gmail.com", userDetailsServiceBeanName = "userDetailsService")
    @Test
    public void getFromAccount_whenReturnView_thenStatus200() throws Exception {
        mockMvc.perform(get("/from-transaction"))
                .andExpect(status().isOk())
                .andExpect(view().name("fromTransaction"));
    }

    @WithUserDetails(value = "yana@gmail.com", userDetailsServiceBeanName = "userDetailsService")
    @Test
    public void postFromAccount_whenReturnFromAccount_thenStatus200() throws Exception {
        LocalDateTime localDateTime = LocalDateTime.of(2023, 1, 3, 18, 56, 0, 712);
        TransactionDTO transactionDTO = new TransactionDTO(1, 1, null, 200, localDateTime);

        List<Integer> categoryList = new ArrayList<>();
        categoryList.add(1);

        when(transactionService.insertFromAccount(1, 2000, 1, categoryList)).thenReturn(transactionDTO);

        mockMvc.perform(post("/from-transaction")
                        .param("fromAccountId", "1")
                        .param("balance", "2000")
                        .param("clientId", "1")
                )
                .andExpect(status().isOk())
                .andExpect(view().name("fromTransaction"));
    }

    @WithUserDetails(value = "yana@gmail.com", userDetailsServiceBeanName = "userDetailsService")
    @Test
    public void getToAccount_whenReturnView_thenStatus200() throws Exception {
        mockMvc.perform(get("/to-transaction"))
                .andExpect(status().isOk())
                .andExpect(view().name("toTransaction"));
    }

    @WithUserDetails(value = "yana@gmail.com", userDetailsServiceBeanName = "userDetailsService")
    @Test
    public void postToAccount_whenReturnToAccount_thenStatus200() throws Exception {
        LocalDateTime localDateTime = LocalDateTime.of(2023, 1, 3, 18, 56, 0, 712);
        TransactionDTO transactionDTO = new TransactionDTO(1, null, 1, 200, localDateTime);

        List<Integer> categoryList = new ArrayList<>();
        categoryList.add(1);

        when(transactionService.insertToAccount(1, 2000, 1, categoryList)).thenReturn(transactionDTO);

        mockMvc.perform(post("/to-transaction")
                        .param("toAccountId", "1")
                        .param("balance", "2000")
                        .param("clientId", "1")
                )
                .andExpect(status().isOk())
                .andExpect(view().name("toTransaction"));
    }

    @WithUserDetails(value = "yana@gmail.com", userDetailsServiceBeanName = "userDetailsService")
    @Test
    public void getFromAccountToAccount_whenReturnView_thenStatus200() throws Exception {
        mockMvc.perform(get("/from-account-to-account-transaction"))
                .andExpect(status().isOk())
                .andExpect(view().name("fromAccountToAccountTransaction"));
    }

    @WithUserDetails(value = "yana@gmail.com", userDetailsServiceBeanName = "userDetailsService")
    @Test
    public void postFromAccountToAccount_whenReturnFromAccountToAccount_thenStatus200() throws Exception {
        LocalDateTime localDateTime = LocalDateTime.of(2023, 1, 3, 18, 56, 0, 712);
        TransactionDTO transactionDTO = new TransactionDTO(1, 2, 1, 200, localDateTime);

        List<Integer> categoryList = new ArrayList<>();
        categoryList.add(1);

        when(transactionService.insertAccountToAccount(1, 2, 2000, 1, categoryList)).thenReturn(transactionDTO);

        mockMvc.perform(post("/from-account-to-account-transaction")
                        .param("toAccountId", "1")
                        .param("toAccountId", "2")
                        .param("balance", "2000")
                        .param("clientId", "1")
                )
                .andExpect(status().isOk())
                .andExpect(view().name("fromAccountToAccountTransaction"));
    }
}