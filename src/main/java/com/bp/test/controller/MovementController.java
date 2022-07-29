package com.bp.test.controller;

import com.bp.test.model.dto.CommonResponseDto;
import com.bp.test.model.dto.movements.MovementDto;
import com.bp.test.model.dto.movements.MovementReportRequest;
import com.bp.test.model.dto.movements.MovementReportResponse;
import com.bp.test.model.enums.ResponseStatusCode;
import com.bp.test.service.MovementService;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.Produces;
import java.util.List;

@RestController
@RequestMapping("/movements")
@AllArgsConstructor
public class MovementController {
    private final MovementService movementService;

    @GetMapping("/{clientId}")
    @Produces("application/json")
    public ResponseEntity<List<MovementReportResponse>> getMovementsByClientId(@PathVariable Long clientId) {
        return ResponseEntity.status(HttpStatus.OK).body(movementService.getMovementsByClientId(clientId));
    }

    @GetMapping("/report")
    @Produces("application/json")
    public ResponseEntity<List<MovementReportResponse>> getMovementsByClientId(@RequestBody MovementReportRequest movementReportRequest) {
        return ResponseEntity.status(HttpStatus.OK).body(movementService.getReportWithIdAndDate(movementReportRequest));
    }

    @PostMapping
    @Produces("application/json")
    public ResponseEntity<CommonResponseDto> createMovement(@RequestBody MovementDto movementDto) {
        movementDto.setMovementId(null);
        if (Boolean.TRUE.equals(StringUtils.isBlank(movementDto.getNumberAccount()) || StringUtils.isBlank(movementDto.getPassword()) || StringUtils.isBlank(movementDto.getIdentification()) || movementDto.getValueMovement() == null)) {
            return ResponseEntity.status(HttpStatus.OK).body(CommonResponseDto.build(ResponseStatusCode.INVALID_PARAMETERS));
        }

        return ResponseEntity.status(HttpStatus.OK).body(movementService.createMovement(movementDto));
    }

    @PutMapping
    @Produces("application/json")
    public ResponseEntity<CommonResponseDto> updateMovement(@RequestBody MovementDto movementDto) {
        if (Boolean.TRUE.equals(StringUtils.isBlank(movementDto.getNumberAccount()) || movementDto.getMovementId() == null || StringUtils.isBlank(movementDto.getPassword()) || StringUtils.isBlank(movementDto.getIdentification()) || movementDto.getValueMovement() == null)) {
            return ResponseEntity.status(HttpStatus.OK).body(CommonResponseDto.build(ResponseStatusCode.INVALID_PARAMETERS));
        }

        return ResponseEntity.status(HttpStatus.OK).body(movementService.updateMovement(movementDto));
    }

    @DeleteMapping
    @Produces("application/json")
    public ResponseEntity<CommonResponseDto> deleteMovement(@RequestBody MovementDto movementDto){
        if (Boolean.TRUE.equals(StringUtils.isBlank(movementDto.getNumberAccount()) || movementDto.getMovementId() == null || StringUtils.isBlank(movementDto.getPassword()) || StringUtils.isBlank(movementDto.getIdentification()))) {
            return ResponseEntity.status(HttpStatus.OK).body(CommonResponseDto.build(ResponseStatusCode.INVALID_PARAMETERS));
        }

        return ResponseEntity.status(HttpStatus.OK).body(movementService.deleteMovement(movementDto));
    }

}
