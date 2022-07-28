package com.bp.test.service;

import com.bp.test.model.dto.CommonResponseDto;
import com.bp.test.model.dto.movements.MovementDto;
import com.bp.test.model.dto.movements.MovementReportResponse;

import java.util.List;

public interface MovementService {

    List<MovementReportResponse> getMovementsByClientId(Long id);
    CommonResponseDto createMovement(MovementDto movement);
    CommonResponseDto updateMovement(MovementDto movement);
    CommonResponseDto deleteMovement(MovementDto movement);
}
