package com.tabatskiy.web.web.controller;

import com.tabatskiy.web.MockSecurityConfiguration;
import com.tabatskiy.web.config.SecurityConfiguration;
import com.tabatskiy.web.service.AccountDTO;
import com.tabatskiy.web.service.AccountService;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AccountWebController.class)
@Import({SecurityConfiguration.class, MockSecurityConfiguration.class})
@RunWith(SpringRunner.class)
public class AccountWebControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    AccountService accountService;

    @WithUserDetails(value = "yana@gmail.com", userDetailsServiceBeanName = "userDetailsService")
    @Test
    public void accounts_whenGetAccounts_thenStatus200() throws Exception {
        AccountDTO accountDTO1 = new AccountDTO(1, "account1", 2000);
        AccountDTO accountDTO2 = new AccountDTO(2, "account2", 2000);

        when(accountService.findAllByClientId(1)).thenReturn(Arrays.asList(accountDTO1, accountDTO2));

        mockMvc.perform(get("/accounts"))
                .andExpect(status().isOk())
                .andExpect(view().name("accountList"))
                .andExpect(model().attributeExists("accounts"))
                .andExpect(model().attribute("accounts", hasSize(2)));
    }

    @WithUserDetails(value = "yana@gmail.com", userDetailsServiceBeanName = "userDetailsService")
    @Test
    public void insertAccountGet_whenReturnView_thenStatus200() throws Exception {
        mockMvc.perform(get("/insert-account"))
                .andExpect(status().isOk())
                .andExpect(view().name("insertAccount"));
    }

    @WithUserDetails(value = "yana@gmail.com", userDetailsServiceBeanName = "userDetailsService")
    @Test
    public void insertAccountPost_whenInsert_thenStatus302() throws Exception {
        AccountDTO accountDTO = new AccountDTO(1, "account", 2000);

        when(accountService.insert("account", 2000, 1)).thenReturn(accountDTO);

        mockMvc.perform(post("/insert-account")
                        .param("name", "account")
                        .param("balance", "2000")
                )
                .andExpect(status().is(302))
                .andExpect(redirectedUrl("/accounts"));
    }

    @WithUserDetails(value = "yana@gmail.com", userDetailsServiceBeanName = "userDetailsService")
    @Test
    public void delete_whenDeleteAccount_thenStatus302() throws Exception {
        AccountDTO accountDTO = new AccountDTO(1, "account1", 2000);

        when(accountService.delete(1, 1)).thenReturn(accountDTO);

        mockMvc.perform(get("/delete/{id}", accountDTO.getId())
                )
                .andExpect(status().is(302))
                .andExpect(redirectedUrl("/accounts"));
    }
}