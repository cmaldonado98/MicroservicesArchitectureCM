package com.bp.test.repository;

import com.bp.test.model.entities.ClientEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;

import java.util.Optional;

public interface ClientRepository extends JpaRepository<ClientEntity, Long> {
    @Query("select c from ClientEntity c where c.identification = ?1")
    Optional<ClientEntity> findByIdentification(@NonNull String identification);

}
