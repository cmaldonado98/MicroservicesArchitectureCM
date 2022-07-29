package com.bp.test.repository;

import com.bp.test.model.entities.MovementsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;
import java.util.List;
import java.util.stream.Stream;

public interface MovementRepository extends JpaRepository<MovementsEntity, Long> {

    @Query("select m from MovementsEntity m where m.account.client.idPerson = ?1")
    Stream<MovementsEntity> findByAccountClientIdPerson(@NonNull Long idPerson);

    @Query(value = "select * from MOVEMENT m JOIN ACCOUNT a on (m.id_account = a.id_account) where DAY_OF_MONTH(create_time) = DAY_OF_MONTH(CURRENT_TIMESTAMP()) and MONTH(create_time) = MONTH(CURRENT_TIMESTAMP()) and movement_type = 'Retiro' and a.account_number = :accountNumber", nativeQuery = true)
    List<MovementsEntity> findByAccountMovementsCreateTime(@Param("accountNumber") String accountNumber);

}
