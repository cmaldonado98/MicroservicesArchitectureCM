package com.bp.test.service;

import com.bp.test.model.dto.AccountDto;
import com.bp.test.model.dto.CommonResponseDto;

import java.util.List;

public interface AccountService {

    List<AccountDto> getAllAccounts(Long clientId);
    CommonResponseDto createAccount(AccountDto accountDto);
}
