package com.tabatskiy.web.service;

import com.tabatskiy.web.converter.ClientModelToClientDtoConverter;
import com.tabatskiy.web.exception.CustomException;
import com.tabatskiy.web.repository.ClientRepository;
import com.tabatskiy.web.service.ClientDTO;
import com.tabatskiy.web.entity.Client;
import com.tabatskiy.web.service.ClientService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.Silent.class)
public class ClientServiceTest {

    @Mock
    ClientRepository clientRepository;

    @Mock
    PasswordEncoder passwordEncoder;

    @Mock
    ClientModelToClientDtoConverter clientDtoConverter;

    @InjectMocks
    ClientService subj;

    @Test(expected = NullPointerException.class)
    public void auth_clientNotFound() {
        when(passwordEncoder.encode("password")).thenReturn("encode");
        when(clientRepository.findByEmailAndPassword("maksaimer@gmail.com", "encode")).thenReturn(null);

        assertNull(subj.auth("maksaimer@gmail.com", "password"));

        verify(passwordEncoder, times(1)).encode("password");
        verify(clientRepository, times(1)).findByEmailAndPassword("maksaimer@gmail.com", "encode");
        verifyZeroInteractions(clientDtoConverter);
    }

    @Test
    public void auth_clientFound() {
        when(passwordEncoder.encode("password")).thenReturn("encode");

        Client clientModel = new Client();
        clientModel.setId(1);
        clientModel.setEmail("maksaimer@gmail.com");
        clientModel.setPassword("encode");
        when(clientRepository.findByEmailAndPassword("maksaimer@gmail.com", "encode")).thenReturn(Optional.of(clientModel));

        ClientDTO clientDTO = new ClientDTO();
        clientDTO.setId(1);
        clientDTO.setEmail("maksaimer@gmail.com");
        when(clientDtoConverter.convert(clientModel)).thenReturn(clientDTO);

        ClientDTO client = subj.auth("maksaimer@gmail.com", "password");
        assertNotNull(client);
        assertEquals(clientDTO, client);
        verify(passwordEncoder, times(1)).encode("password");
        verify(clientRepository, times(1)).findByEmailAndPassword("maksaimer@gmail.com", "encode");
        verify(clientDtoConverter, times(1)).convert(clientModel);
    }

    @Test
    public void registration_clientNotRegistration() {
        Client client = new Client();
        client.setEmail("maksaimer@gmail.com");
        client.setPassword("encode");
        when(clientRepository.save(client)).thenThrow(CustomException.class);

        verifyZeroInteractions(passwordEncoder);
        verifyZeroInteractions(clientRepository);
        verifyZeroInteractions(clientDtoConverter);
    }

    @Test
    public void registration_clientRegistration() {
        when(passwordEncoder.encode("password")).thenReturn("encode");

        Client clientModel = new Client();
        clientModel.setEmail("maksaimer@gmail.com");
        clientModel.setPassword("encode");
        when(clientRepository.save(clientModel)).thenReturn(clientModel);

        ClientDTO clientDTO = new ClientDTO();
        clientDTO.setId(1);
        clientDTO.setEmail("maksaimer@gmail.com");
        when(clientDtoConverter.convert(clientModel)).thenReturn(clientDTO);

        ClientDTO client = subj.registration("maksaimer@gmail.com", "password");
        assertNotNull(client);
        assertEquals(clientDTO, client);

        verify(passwordEncoder, times(1)).encode("password");
        verify(clientRepository, times(1)).save(clientModel);
        verify(clientDtoConverter, times(1)).convert(clientModel);
    }
}