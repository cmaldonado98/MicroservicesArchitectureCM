package com.bp.test.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import static lombok.AccessLevel.PRIVATE;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = PRIVATE)
public class AccountDto {
    Long accountId;
    String accountNumber;
    String identification;
    String password;
    String type;
    Double initialBalance;
    Boolean status;
    String nameClient;
}
