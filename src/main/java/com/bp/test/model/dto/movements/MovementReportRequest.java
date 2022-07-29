package com.bp.test.model.dto.movements;

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
public class MovementReportRequest {

    String startDate;
    String endDate;
    Long clientId;
}
