package com.tabatskiy.web.web.controller;

import com.tabatskiy.web.MockSecurityConfiguration;
import com.tabatskiy.web.config.SecurityConfiguration;
import com.tabatskiy.web.service.ClientDTO;
import com.tabatskiy.web.service.ClientService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ClientWebController.class)
@Import({SecurityConfiguration.class, MockSecurityConfiguration.class})
@RunWith(SpringRunner.class)
public class ClientWebControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    ClientService clientService;

    @Test
    public void getLogin() throws Exception {
        mockMvc.perform(get("/login-form"))
                .andExpect(status().isOk())
                .andExpect(view().name("login-form"));
    }

    @WithUserDetails(value = "yana@gmail.com", userDetailsServiceBeanName = "userDetailsService")
    @Test
    public void index() throws Exception {
        when(clientService.currentClient()).thenReturn(
                new ClientDTO(1, "yana@gmail.com")
        );

        mockMvc.perform(get("/personal-area"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("id", 1))
                .andExpect(model().attribute("name", "yana@gmail.com"))
                .andExpect(view().name("personal-area"));
    }

    @Test
    public void getRegistration() throws Exception {
        mockMvc.perform(get("/regist"))
                .andExpect(status().isOk())
                .andExpect(view().name("registration"));
    }

    @Test
    public void postRegistration() throws Exception {
        when(clientService.registration("yana@gmail.com", "12345")).thenReturn(
                new ClientDTO(1, "yana@gmail.com")
        );

        mockMvc.perform(get("/regist"))
                .andExpect(status().isOk())
                .andExpect(view().name("registration"));
    }
}