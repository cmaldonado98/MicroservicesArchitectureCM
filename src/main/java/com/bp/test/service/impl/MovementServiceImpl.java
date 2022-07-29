package com.bp.test.service.impl;

import com.bp.test.exception.ApplicationException;
import com.bp.test.model.dto.CommonResponseDto;
import com.bp.test.model.dto.movements.MovementDto;
import com.bp.test.model.dto.movements.MovementReportRequest;
import com.bp.test.model.dto.movements.MovementReportResponse;
import com.bp.test.model.entities.AccountEntity;
import com.bp.test.model.entities.ClientEntity;
import com.bp.test.model.entities.MovementsEntity;
import com.bp.test.model.enums.ResponseStatusCode;
import com.bp.test.repository.AccountRepository;
import com.bp.test.repository.ClientRepository;
import com.bp.test.repository.MovementRepository;
import com.bp.test.service.MovementService;
import com.bp.test.service.mapper.GeneralMapper;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.bp.test.model.enums.ResponseStatusCode.OK;
import static com.bp.test.util.Constant.*;

@Service
@AllArgsConstructor
@Log4j2
public class MovementServiceImpl implements MovementService {

    private final MovementRepository movementRepository;

    private final ClientRepository clientRepository;
    private final AccountRepository accountRepository;

    private final GeneralMapper mapper;

    @Override
    @Transactional(readOnly = true)
    public List<MovementReportResponse> getMovementsByClientId(Long id) {
        log.info("Obtaining all movements by ID");
        return movementRepository.findByAccountClientIdPerson(id).map(mapper::mapMovement).collect(Collectors.toList());

    }

    @Override
    @Transactional(readOnly = true)
    public List<MovementReportResponse> getReportWithIdAndDate(MovementReportRequest movementReportRequest) {
        log.info("Obtaining all report movements by ID and Date");
        return movementRepository.findByAccountClientIdPerson
                (movementReportRequest.getClientId())
                .filter(x -> x.getCreateTime().isAfter(LocalDateTime.parse(movementReportRequest.getStartDate())) && x.getCreateTime().isBefore(LocalDateTime.parse(movementReportRequest.getEndDate())))
                .map(mapper::mapMovement).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public CommonResponseDto createMovement(MovementDto movement) {
        log.info(String.format("Creating movement with number account: %s", movement.getNumberAccount()));
        Optional<ClientEntity> clientEntity = clientRepository.findByIdentification(movement.getIdentification());
        if (clientEntity.isPresent() && clientEntity.get().getPassword().equals(movement.getPassword())) {
            Optional<AccountEntity> account = accountRepository.findByClientAccountNumber(movement.getNumberAccount());
            if (account.isPresent() && clientEntity.get().getIdPerson().equals(account.get().getClient().getIdPerson())) {

                if (movement.getValueMovement() < 0 && Math.abs(movement.getValueMovement()) > account.get().getInitialBalance()) {
                    throw new ApplicationException(ResponseStatusCode.ACCOUNT_HAS_INSUFFICIENT_BALANCE);
                } else if (movement.getValueMovement() < 0 && Math.abs(movement.getValueMovement()) < account.get().getInitialBalance()) {

                    List<MovementsEntity> movementsEntityList = movementRepository.findByAccountMovementsCreateTime();
                    double limitAmount = movementsEntityList.stream().mapToDouble(MovementsEntity::getMovementValue).sum();
                    if ((Math.abs(limitAmount) + Math.abs(movement.getValueMovement())) > WITHDRAW_LIMIT) {
                        throw new ApplicationException(ResponseStatusCode.ACCOUNT_EXCEED_LIMIT);
                    }
                    MovementsEntity movementsEntity = new MovementsEntity();
                    movementsEntity.setMovementType(WITHDRAWAL);
                    movementsEntity.setMovementValue(movement.getValueMovement());
                    movementsEntity.setCreateTime(LocalDateTime.now());
                    movementsEntity.setMovementInitialBalance(account.get().getInitialBalance());
                    movementsEntity.setBalance(account.get().getInitialBalance() + movement.getValueMovement());
                    movementsEntity.setAccount(account.get());
                    movementRepository.save(movementsEntity);
                    account.get().setInitialBalance(account.get().getInitialBalance() + movement.getValueMovement());
                    accountRepository.save(account.get());
                    return CommonResponseDto.build(OK);
                }

                if (movement.getValueMovement() > 0) {
                    MovementsEntity movementsEntity = new MovementsEntity();
                    movementsEntity.setMovementType(DEPOSIT);
                    movementsEntity.setMovementValue(movement.getValueMovement());
                    movementsEntity.setCreateTime(LocalDateTime.now());
                    movementsEntity.setMovementInitialBalance(account.get().getInitialBalance());
                    movementsEntity.setBalance(account.get().getInitialBalance() + movement.getValueMovement());
                    movementsEntity.setAccount(account.get());
                    account.get().setInitialBalance(account.get().getInitialBalance() + movement.getValueMovement());
                    accountRepository.save(account.get());
                    movementRepository.save(movementsEntity);
                    return CommonResponseDto.build(OK);
                }


            }
            throw new ApplicationException(ResponseStatusCode.INVALID_USER_FOR_ACCOUNT);


        }
        throw new ApplicationException(ResponseStatusCode.PASSWORD_OR_CLIENT_INCORRECT);
    }

    @Override
    public CommonResponseDto updateMovement(MovementDto movement){
        log.info(String.format("Updating movement with id: %s", movement.getMovementId().toString()));
        Optional<ClientEntity> clientEntity = clientRepository.findByIdentification(movement.getIdentification());
        if (clientEntity.isPresent() && clientEntity.get().getPassword().equals(movement.getPassword())) {
            Optional<AccountEntity> account = accountRepository.findByClientAccountNumber(movement.getNumberAccount());
            if (account.isPresent() && clientEntity.get().getIdPerson().equals(account.get().getClient().getIdPerson())) {

                if (movement.getValueMovement() < 0 && Math.abs(movement.getValueMovement()) > account.get().getInitialBalance()) {
                    throw new ApplicationException(ResponseStatusCode.ACCOUNT_HAS_INSUFFICIENT_BALANCE);
                } else if (movement.getValueMovement() < 0 && Math.abs(movement.getValueMovement()) < account.get().getInitialBalance()) {

                    List<MovementsEntity> movementsEntityList = movementRepository.findByAccountMovementsCreateTime();
                    double limitAmount = movementsEntityList.stream().mapToDouble(MovementsEntity::getMovementValue).sum();
                    if ((Math.abs(limitAmount) + Math.abs(movement.getValueMovement())) > WITHDRAW_LIMIT) {
                        throw new ApplicationException(ResponseStatusCode.ACCOUNT_EXCEED_LIMIT);
                    }
                    MovementsEntity movementsEntity = movementRepository.findById(movement.getMovementId())
                            .orElseThrow(() -> {
                                log.error(String.format("Movement to be updated not found id: %s", movement.getMovementId().toString()));
                                return new ApplicationException(ResponseStatusCode.MOVEMENT_DOES_NOT_EXISTS);
                            });
                    movementsEntity.setMovementType(WITHDRAWAL);
                    movementsEntity.setMovementValue(movement.getValueMovement());
                    movementsEntity.setCreateTime(LocalDateTime.now());
                    movementsEntity.setMovementInitialBalance(account.get().getInitialBalance());
                    movementsEntity.setBalance(account.get().getInitialBalance() + movement.getValueMovement());
                    movementsEntity.setAccount(account.get());
                    movementRepository.save(movementsEntity);
                    account.get().setInitialBalance(account.get().getInitialBalance() + movement.getValueMovement());
                    accountRepository.save(account.get());
                    return CommonResponseDto.build(OK);
                }

                if (movement.getValueMovement() > 0) {
                    MovementsEntity movementsEntity = movementRepository.findById(movement.getMovementId())
                            .orElseThrow(() -> {
                                log.error(String.format("Movement to be updated not found id: %s", movement.getMovementId().toString()));
                                return new ApplicationException(ResponseStatusCode.MOVEMENT_DOES_NOT_EXISTS);
                            });
                    movementsEntity.setMovementType(DEPOSIT);
                    movementsEntity.setMovementValue(movement.getValueMovement());
                    movementsEntity.setCreateTime(LocalDateTime.now());
                    movementsEntity.setMovementInitialBalance(account.get().getInitialBalance());
                    movementsEntity.setBalance(account.get().getInitialBalance() + movement.getValueMovement());
                    movementsEntity.setAccount(account.get());
                    account.get().setInitialBalance(account.get().getInitialBalance() + movement.getValueMovement());
                    accountRepository.save(account.get());
                    movementRepository.save(movementsEntity);
                    return CommonResponseDto.build(OK);
                }


            }
            throw new ApplicationException(ResponseStatusCode.INVALID_USER_FOR_ACCOUNT);


        }
        throw new ApplicationException(ResponseStatusCode.PASSWORD_OR_CLIENT_INCORRECT);
    }

    @Override
    public CommonResponseDto deleteMovement(MovementDto movement){

        Optional<ClientEntity> clientEntity = clientRepository.findByIdentification(movement.getIdentification());
        if (clientEntity.isPresent() && clientEntity.get().getPassword().equals(movement.getPassword())) {
            Optional<AccountEntity> account = accountRepository.findByClientAccountNumber(movement.getNumberAccount());
            if (account.isPresent() && clientEntity.get().getIdPerson().equals(account.get().getClient().getIdPerson())) {

                MovementsEntity movementsEntity = movementRepository.findById(movement.getMovementId())
                        .orElseThrow(() -> {
                            log.error(String.format("Movement to be deleted not found id: %s", movement.getMovementId().toString()));
                            return new ApplicationException(ResponseStatusCode.MOVEMENT_DOES_NOT_EXISTS);
                        });

                account.get().setInitialBalance(account.get().getInitialBalance() - movementsEntity.getMovementValue());
                accountRepository.save(account.get());
                movementRepository.delete(movementsEntity);
                return CommonResponseDto.build(OK);
            }
            throw new ApplicationException(ResponseStatusCode.INVALID_USER_FOR_ACCOUNT);


        }
        throw new ApplicationException(ResponseStatusCode.PASSWORD_OR_CLIENT_INCORRECT);
    }

}
