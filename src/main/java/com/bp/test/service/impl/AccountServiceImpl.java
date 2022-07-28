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
    public List<AccountDto> getAllAccounts(Long clientId) {
        log.info("Obtaining all accounts");
        return accountRepository.getAccountsWithClient(clientId).map(mapper::mapAccount).collect(Collectors.toList());

    }

    @Override
    @Transactional
    public CommonResponseDto createAccount(AccountDto accountDto){

        Optional<ClientEntity> clientEntity = clientRepository.findByIdentification(accountDto.getIdentification());
        if(clientEntity.isPresent() && clientEntity.get().getPassword().equals(accountDto.getPassword())){
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
        throw new ApplicationException(ResponseStatusCode.PASSWORD_DOES_NOT_MATCH);
    }
}
