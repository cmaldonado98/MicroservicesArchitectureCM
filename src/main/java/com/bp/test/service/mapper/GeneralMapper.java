package com.bp.test.service.mapper;

import com.bp.test.model.dto.ClientDto;
import com.bp.test.model.entities.ClientEntity;
import org.springframework.stereotype.Component;

@Component
public class GeneralMapper {

    public ClientDto mapClient(ClientEntity client){
        return ClientDto.builder()
                .clientId(client.getIdPerson())
                .name(client.getName())
                .address(client.getAddress())
                .phone(client.getPhone())
                .password(client.getPassword())
                .status(client.getStatus())
                .identification(client.getIdentification())
                .build();
    }
}
