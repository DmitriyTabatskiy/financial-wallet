package com.tabatskiy.web.converter;

import com.tabatskiy.web.entity.Client;
import com.tabatskiy.web.service.ClientDTO;
import org.springframework.stereotype.Service;

@Service
public class ClientModelToClientDtoConverter implements Converter<Client, ClientDTO> {

    @Override
    public ClientDTO convert(Client source) {
        ClientDTO clientDTO = new ClientDTO();
        clientDTO.setId(source.getId());
        clientDTO.setEmail(source.getEmail());
        return clientDTO;
    }
}