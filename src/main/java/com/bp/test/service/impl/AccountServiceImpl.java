package com.bp.test.service.impl;

import com.bp.test.exception.ApplicationException;
import com.bp.test.model.dto.AccountDto;
import com.bp.test.model.dto.CommonResponseDto;
import com.bp.test.model.entities.AccountEntity;
import com.bp.test.model.entities.ClientEntity;
import com.bp.test.model.enums.ResponseStatusCode;
import com.bp.test.repository.AccountRepository;
import com.bp.test.repository.ClientRepository;
import com.bp.test.service.AccountService;
import com.bp.test.service.mapper.GeneralMapper;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.bp.test.model.enums.ResponseStatusCode.OK;

@Service
@AllArgsConstructor
@Log4j2
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final ClientRepository clientRepository;
    private final GeneralMapper mapper;

    @Override
    @Transactional(readOnly = true)
    public List<AccountDto> getAccountsById(Long clientId) {
        log.info("Obtaining all accounts by client ID");
        return accountRepository.getAccountsWithClient(clientId).map(mapper::mapAccount).collect(Collectors.toList());

    }

    @Override
    @Transactional
    public CommonResponseDto createAccount(AccountDto accountDto) {
        log.info(String.format("Creating account with number: %s", accountDto.getAccountNumber()));

        Optional<ClientEntity> clientEntity = clientRepository.findByIdentification(accountDto.getIdentification());
        if (clientEntity.isPresent() && clientEntity.get().getPassword().equals(accountDto.getPassword())) {
            AccountEntity newAccount = new AccountEntity();
            newAccount.setAccountNumber(accountDto.getAccountNumber());
            newAccount.setAccountType(accountDto.getType());
            newAccount.setInitialBalance(accountDto.getInitialBalance());
            newAccount.setAccountStatus(accountDto.getStatus());
            newAccount.setClient(clientEntity.get());
            accountRepository.save(newAccount);

            return CommonResponseDto.builder()
                    .code(OK.getCode())
                    .message(OK.getMessage())
                    .response(String.format("ID: %s Name: %s", newAccount.getIdAccount(), newAccount.getClient().getName()))
                    .build();
        }
        throw new ApplicationException(ResponseStatusCode.PASSWORD_OR_CLIENT_INCORRECT);
    }

    @Override
    public CommonResponseDto updateAccount(AccountDto account) {
        log.info(String.format("Updating account with id: %s", account.getAccountId().toString()));

        if (Boolean.TRUE.equals(validateUserAndPassword(account.getIdentification(), account.getPassword()))) {
            AccountEntity accountEntity = accountRepository.findById(account.getAccountId())
                    .orElseThrow(() -> {
                        log.error(String.format("Account to be updated not found id: %s", account.getAccountId().toString()));
                        return new ApplicationException(ResponseStatusCode.ACCOUNT_DOES_NOT_EXISTS);
                    });
            Optional<ClientEntity> clientEntity = clientRepository.findByIdentification(account.getIdentification());
            if (clientEntity.isPresent() && clientEntity.get().getIdPerson().equals(accountEntity.getClient().getIdPerson())) {
                accountEntity.setAccountNumber(account.getAccountNumber());
                accountEntity.setAccountType(account.getType());
                accountEntity.setInitialBalance(account.getInitialBalance());
                accountEntity.setAccountStatus(account.getStatus());
                accountRepository.save(accountEntity);
                return CommonResponseDto.build(OK);
            }
            throw new ApplicationException(ResponseStatusCode.INVALID_USER_FOR_ACCOUNT);
        }
        throw new ApplicationException(ResponseStatusCode.PASSWORD_OR_CLIENT_INCORRECT);
    }

    @Override
    public CommonResponseDto deleteAccount(AccountDto account) {
        log.info(String.format("Deleting account with id: %s", account.getAccountId().toString()));
        if (Boolean.TRUE.equals(validateUserAndPassword(account.getIdentification(), account.getPassword()))) {
            AccountEntity accountEntity = accountRepository.findById(account.getAccountId())
                    .orElseThrow(() -> {
                        log.error(String.format("Account to be deleted not found id: %s", account.getAccountId().toString()));
                        return new ApplicationException(ResponseStatusCode.ACCOUNT_DOES_NOT_EXISTS);
                    });
            Optional<ClientEntity> clientEntity = clientRepository.findByIdentification(account.getIdentification());
            if (clientEntity.isPresent() && clientEntity.get().getIdPerson().equals(accountEntity.getClient().getIdPerson())) {
                accountRepository.delete(accountEntity);
                return CommonResponseDto.build(OK);
            }
            throw new ApplicationException(ResponseStatusCode.INVALID_USER_FOR_ACCOUNT);
        }

        throw new ApplicationException(ResponseStatusCode.PASSWORD_OR_CLIENT_INCORRECT);
    }

    private Boolean validateUserAndPassword(String identification, String password) {
        Optional<ClientEntity> clientEntity = clientRepository.findByIdentification(identification);
        if (clientEntity.isPresent() && clientEntity.get().getPassword().equals(password)) {
            return true;
        }
        return false;
    }

}
