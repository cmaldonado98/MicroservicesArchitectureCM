package com.bp.test.service;

import com.bp.test.exception.ApplicationException;
import com.bp.test.model.dto.AccountDto;
import com.bp.test.model.entities.AccountEntity;
import com.bp.test.model.entities.ClientEntity;
import com.bp.test.repository.AccountRepository;
import com.bp.test.repository.ClientRepository;
import com.bp.test.service.impl.AccountServiceImpl;
import com.bp.test.service.mapper.GeneralMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;
import java.util.Optional;
import java.util.stream.Stream;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = AccountServiceImpl.class)
class AccountServiceTest {

    @Autowired
    private AccountService accountService;
    @MockBean
    private AccountRepository accountRepository;
    @MockBean
    private ClientRepository clientRepository;
    @MockBean
    private GeneralMapper mapper;

    void mockAccountClientOK(){
        AccountEntity accountEntity = new AccountEntity();
        accountEntity.setIdAccount(1L);
        accountEntity.setAccountType("Ahorros");
        accountEntity.setInitialBalance(200.00);
        accountEntity.setAccountStatus(Boolean.TRUE);
        ClientEntity clientEntity = new ClientEntity();
        clientEntity.setIdPerson(1L);
        clientEntity.setPassword("1234567");
        clientEntity.setStatus(Boolean.TRUE);
        clientEntity.setAccounts(Collections.singletonList(accountEntity));
        Mockito.when(clientRepository.findByIdentification(Mockito.anyString()))
                .thenReturn(Optional.of(clientEntity));
    }

    @Test
    void getAccountsByIdIsOK() {
        Mockito.when(accountRepository.getAccountsWithClient(Mockito.anyLong()))
                .thenReturn(Stream.empty());
        Assertions.assertDoesNotThrow(() -> accountService.getAccountsById(2L));
    }

    @Test
    void createAccountThrowPasswordError() {
        Mockito.when(clientRepository.findByIdentification(Mockito.anyString()))
                .thenReturn(Optional.empty());
        Assertions.assertThrows(ApplicationException.class, () -> accountService.createAccount(AccountDto.builder()
                        .identification("1234567890")
                .build()));
    }

    @Test
    void createAccountIsOK() {
        mockAccountClientOK();
        Assertions.assertDoesNotThrow(() -> accountService.createAccount(AccountDto.builder()
                .identification("1234567890")
                .password("1234567")
                .build()));
    }

    @Test
    void updateAccountError() {
        mockAccountClientOK();
        Assertions.assertThrows(ApplicationException.class, () -> accountService.updateAccount(AccountDto.builder()
                .identification("1234567890")
                .password("1234567")
                        .accountId(1L)
                .build()));

    }

    @Test
    void updateAccountIsOK() {
        ClientEntity clientEntity = new ClientEntity();
        AccountEntity accountEntity = new AccountEntity();
        clientEntity.setIdPerson(1L);
        accountEntity.setIdAccount(1L);
        accountEntity.setAccountType("Ahorros");
        accountEntity.setInitialBalance(200.00);
        accountEntity.setAccountStatus(Boolean.TRUE);
        accountEntity.setClient(clientEntity);
        clientEntity.setPassword("1234567");
        clientEntity.setStatus(Boolean.TRUE);
        clientEntity.setAccounts(Collections.singletonList(accountEntity));
        Mockito.when(accountRepository.findById(Mockito.anyLong()))
                .thenReturn(Optional.of(accountEntity));
        Mockito.when(clientRepository.findByIdentification(Mockito.anyString()))
                .thenReturn(Optional.of(clientEntity));
        Assertions.assertDoesNotThrow(() -> accountService.updateAccount(AccountDto.builder()
                .identification("1234567890")
                .password("1234567")
                .accountId(1L)
                .build()));

    }
}