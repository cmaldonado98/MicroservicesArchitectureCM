package com.bp.test.service;

import com.bp.test.model.dto.ClientDto;
import com.bp.test.model.dto.CommonResponseDto;

import java.util.List;

public interface ClientService {

    List<ClientDto> getAllClients();
    CommonResponseDto createClient(ClientDto client);
    CommonResponseDto updateClient(ClientDto client);
    CommonResponseDto deleteClient(Long clientId);
}
