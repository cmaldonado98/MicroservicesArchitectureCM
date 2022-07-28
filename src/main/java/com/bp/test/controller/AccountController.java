package com.bp.test.controller;

import com.bp.test.model.dto.AccountDto;
import com.bp.test.model.dto.CommonResponseDto;
import com.bp.test.model.enums.ResponseStatusCode;
import com.bp.test.service.AccountService;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.Produces;
import java.util.List;

@RestController
@RequestMapping("/account")
@AllArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @GetMapping("/{clientId}")
    @Produces("application/json")
    public ResponseEntity<List<AccountDto>> getAccountsById(@PathVariable Long clientId) {
        return ResponseEntity.status(HttpStatus.OK).body(accountService.getAccountsById(clientId));
    }

    @PostMapping
    @Produces("application/json")
    public ResponseEntity<CommonResponseDto> createAccount(@RequestBody AccountDto account) {
        account.setAccountId(null);
        if (Boolean.TRUE.equals(StringUtils.isBlank(account.getAccountNumber())) || StringUtils.isBlank(account.getPassword()) || StringUtils.isBlank(account.getIdentification())) {
            return ResponseEntity.status(HttpStatus.OK).body(CommonResponseDto.build(ResponseStatusCode.INVALID_PARAMETERS));
        }

        if (account.getInitialBalance() < 0) {
            return ResponseEntity.status(HttpStatus.OK).body(CommonResponseDto.build(ResponseStatusCode.INVALID_AMOUNT));
        }

        return ResponseEntity.status(HttpStatus.OK).body(accountService.createAccount(account));
    }

    @PutMapping
    @Produces("application/json")
    public ResponseEntity<CommonResponseDto> updateClient(@RequestBody AccountDto account){
        if (Boolean.TRUE.equals(StringUtils.isBlank(account.getAccountNumber()) || StringUtils.isBlank(account.getPassword()) || StringUtils.isBlank(account.getIdentification()))) {
            return ResponseEntity.status(HttpStatus.OK).body(CommonResponseDto.build(ResponseStatusCode.INVALID_PARAMETERS));
        }

        return ResponseEntity.status(HttpStatus.OK).body(accountService.updateAccount(account));
    }

    @DeleteMapping
    @Produces("application/json")
    public ResponseEntity<CommonResponseDto> deleteAccount(@RequestBody AccountDto account) {

        return ResponseEntity.status(HttpStatus.OK).body(accountService.deleteAccount(account));
    }
}
