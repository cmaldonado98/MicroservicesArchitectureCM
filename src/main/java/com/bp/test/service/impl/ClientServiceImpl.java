package com.bp.test.service.impl;

import com.bp.test.exception.ApplicationException;
import com.bp.test.model.dto.ClientDto;
import com.bp.test.model.dto.CommonResponseDto;
import com.bp.test.model.entities.ClientEntity;
import com.bp.test.model.enums.ResponseStatusCode;
import com.bp.test.repository.ClientRepository;
import com.bp.test.service.ClientService;
import com.bp.test.service.mapper.GeneralMapper;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static com.bp.test.model.enums.ResponseStatusCode.OK;

@Service
@AllArgsConstructor
@Log4j2
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;

    private final GeneralMapper mapper;

    @Override
    @Transactional(readOnly = true)
    public List<ClientDto> getAllClients() {
        log.info("Obtaining all clients");
        return clientRepository.findAll().stream().map(mapper::mapClient).collect(Collectors.toList());

    }

    @Override
    @Transactional
    public CommonResponseDto createClient(ClientDto client) {
        log.info(String.format("Creating client with name: %s and status: %s", client.getName(), client.getStatus().toString()));

        ClientEntity newClient = new ClientEntity();
        newClient.setName(client.getName());
        newClient.setAddress(client.getAddress());
        newClient.setPhone(client.getPhone());
        newClient.setPassword(client.getPassword());
        newClient.setStatus(client.getStatus());

        newClient = clientRepository.save(newClient);


        return CommonResponseDto.builder()
                .code(OK.getCode())
                .message(OK.getMessage())
                .response(String.format("ID: %s Name: %s", newClient.getIdPerson(), newClient.getName()))
                .build();
    }

    @Override
    public CommonResponseDto updateClient(ClientDto client){
        log.info(String.format("Updating client with id: %s", client.getClientId().toString()));

        ClientEntity clientEntity = clientRepository.findById(client.getClientId())
                .orElseThrow(()-> {
                    log.error(String.format("Client to be updated not found id: %s", client.getClientId().toString()));
                    return new ApplicationException(ResponseStatusCode.CLIENT_DOES_NOT_EXISTS);
                });
        clientEntity.setName(client.getName());
        clientEntity.setAddress(client.getAddress());
        clientEntity.setPhone(client.getPhone());
        clientEntity.setPassword(client.getPassword());
        clientEntity.setStatus(client.getStatus());
        clientRepository.save(clientEntity);

        return CommonResponseDto.build(OK);
    }

    @Override
    public CommonResponseDto deleteClient(Long clientId){
        log.info(String.format("Deleting client with id: %s", clientId.toString()));

        ClientEntity clientEntity = clientRepository.findById(clientId)
                .orElseThrow(()-> {
                    log.error(String.format("Client to be deleted not found id: %s", clientId.toString()));
                    return new ApplicationException(ResponseStatusCode.CLIENT_DOES_NOT_EXISTS);
                });
        clientRepository.delete(clientEntity);
        return CommonResponseDto.build(OK);
    }
}
