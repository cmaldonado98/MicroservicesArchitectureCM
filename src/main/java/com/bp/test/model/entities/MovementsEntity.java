package com.bp.test.model.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import java.time.LocalDateTime;

import static lombok.AccessLevel.PRIVATE;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "movement")
@FieldDefaults(level = PRIVATE)
public class MovementsEntity {

    @Id
    @Column(name = "id_movement", nullable = false)
    Long idMovement;

    @Column(name = "create_time", nullable = false)
    LocalDateTime createTime;

    @Column(name = "movement_type", nullable = false)
    String movementType;

    @Column(name = "movement_value", nullable = false)
    Double movementValue;

    @Column(name = "balance", nullable = false)
    Double balance;


}
