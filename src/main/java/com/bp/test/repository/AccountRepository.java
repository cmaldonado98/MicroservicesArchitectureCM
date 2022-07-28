package com.bp.test.repository;

import com.bp.test.model.entities.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;

import java.util.Optional;
import java.util.stream.Stream;

public interface AccountRepository extends JpaRepository<AccountEntity, Long> {

    @Query("Select a from AccountEntity a "
        + "join a.client c "
        + "where a.client.idPerson = :clientId")
    Stream<AccountEntity> getAccountsWithClient(@Param("clientId") Long clientId);

    @Query("select a from AccountEntity a inner join a.client.accounts accounts where accounts.accountNumber = ?1")
    Optional<AccountEntity> findByClientAccountNumber(@NonNull String accountNumber);




}
