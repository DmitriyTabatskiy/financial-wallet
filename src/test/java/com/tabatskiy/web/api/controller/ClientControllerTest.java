package com.tabatskiy.web.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.tabatskiy.web.MockSecurityConfiguration;
import com.tabatskiy.web.api.json.client.ClientRequest;
import com.tabatskiy.web.config.SecurityConfiguration;

import com.tabatskiy.web.service.ClientDTO;
import com.tabatskiy.web.service.ClientService;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ClientController.class)
@Import({SecurityConfiguration.class, MockSecurityConfiguration.class})
@RunWith(SpringRunner.class)
public class ClientControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    ClientService clientService;

    @Test
    public void registration_whenRegistration_thenStatus200() throws Exception {
        ClientDTO clientDTO = new ClientDTO(1, "yana@gmail.com");

        when(clientService.registration("yana@gmail.com", "12345")).thenReturn(clientDTO);

        ObjectWriter objectWriter = objectMapper.writer();

        mockMvc.perform(post("/api/registration")
                        .contentType(APPLICATION_JSON)
                        .content(objectWriter.writeValueAsString(new ClientRequest("yana@gmail.com", "12345"))))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(clientDTO)));
    }
}