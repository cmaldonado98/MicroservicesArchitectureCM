package com.bp.test.controller;


import com.bp.test.model.dto.ClientDto;
import com.bp.test.model.dto.CommonResponseDto;
import com.bp.test.model.enums.ResponseStatusCode;
import com.bp.test.service.ClientService;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.Produces;
import java.util.List;

@RestController
@RequestMapping("/client")
@AllArgsConstructor
public class ClientController {

    private final ClientService clientService;

    @GetMapping("/all")
    @Produces("application/json")
    public ResponseEntity<List<ClientDto>> getClients() {
        return ResponseEntity.status(HttpStatus.OK).body(clientService.getAllClients());
    }

    @PostMapping
    @Produces("application/json")
    public ResponseEntity<CommonResponseDto> createClient(@RequestBody ClientDto client) {
        client.setClientId(null);

        if (Boolean.TRUE.equals(StringUtils.isBlank(client.getName())) || StringUtils.isBlank(client.getPassword())) {
            return ResponseEntity.status(HttpStatus.OK).body(CommonResponseDto.build(ResponseStatusCode.INVALID_PARAMETERS));
        }

        return ResponseEntity.status(HttpStatus.OK).body(clientService.createClient(client));

    }

    @PutMapping
    @Produces("application/json")
    public ResponseEntity<CommonResponseDto> updateClient(@RequestBody ClientDto client) {

        if (Boolean.TRUE.equals(StringUtils.isBlank(client.getName())) || StringUtils.isBlank(client.getPassword())) {
            return ResponseEntity.status(HttpStatus.OK).body(CommonResponseDto.build(ResponseStatusCode.INVALID_PARAMETERS));
        }

        return ResponseEntity.status(HttpStatus.OK).body(clientService.updateClient(client));

    }

    @DeleteMapping("/{id}")
    @Produces("application/json")
    @Transactional
    public ResponseEntity<CommonResponseDto> deleteClient(@PathVariable Long id) {

        return ResponseEntity.status(HttpStatus.OK).body(clientService.deleteClient(id));
    }


}
