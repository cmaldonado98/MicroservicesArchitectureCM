package com.bp.test.model.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;

import static lombok.AccessLevel.PRIVATE;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "account")
@FieldDefaults(level = PRIVATE)
public class AccountEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_account", nullable = false)
    Long idAccount;

    @ManyToOne
    @JoinColumn(name = "client_id")
    ClientEntity client;

    @Column(name = "account_number")
    String accountNumber;

    @Column(name = "account_type")
    String accountType;

    @Column(name = "initial_balance")
    Double initialBalance;

    @Column(name = "account_status")
    Boolean accountStatus;
}
