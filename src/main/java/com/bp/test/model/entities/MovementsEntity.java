package com.bp.test.model.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;

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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_movement", nullable = false)
    Long idMovement;

    @Column(name = "create_time", nullable = false)
    LocalDateTime createTime;

    @Column(name = "movement_type", nullable = false)
    String movementType;

    @Column(name = "movement_initial_balance", nullable = false)
    Double movementInitialBalance;

    @Column(name = "movement_value", nullable = false)
    Double movementValue;

    @Column(name = "balance", nullable = false)
    Double balance;

    @ManyToOne
    @JoinColumn(name = "id_account")
    AccountEntity account;


}
