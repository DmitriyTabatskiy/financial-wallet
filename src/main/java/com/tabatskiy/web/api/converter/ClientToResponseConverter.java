package com.tabatskiy.web.api.converter;

import com.tabatskiy.web.api.json.client.ClientResponse;
import com.tabatskiy.web.service.ClientDTO;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ClientToResponseConverter implements Converter<ClientDTO, ClientResponse> {

    @Override
    public ClientResponse convert(ClientDTO client) {
        return new ClientResponse(client.getId(), client.getEmail());
    }
}