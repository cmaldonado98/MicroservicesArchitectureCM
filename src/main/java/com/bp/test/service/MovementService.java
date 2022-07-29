package com.bp.test.service;

import com.bp.test.model.dto.CommonResponseDto;
import com.bp.test.model.dto.movements.MovementDto;
import com.bp.test.model.dto.movements.MovementReportRequest;
import com.bp.test.model.dto.movements.MovementReportResponse;

import java.util.List;

public interface MovementService {

    List<MovementReportResponse> getMovementsByClientId(Long id);

    List<MovementReportResponse> getReportWithIdAndDate(MovementReportRequest movementReportRequest);
    CommonResponseDto createMovement(MovementDto movement);
    CommonResponseDto updateMovement(MovementDto movement);
    CommonResponseDto deleteMovement(MovementDto movement);
}

