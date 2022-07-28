package com.bp.test.model.dto.movements;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

import static lombok.AccessLevel.PRIVATE;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = PRIVATE)
public class MovementReportResponse {
    LocalDateTime createDate;
    String nameClient;
    String numberAccount;
    String typeAccount;
    Double initialBalance;
    Boolean accountStatus;
    Double valueMovement;
    Double balance;
}
