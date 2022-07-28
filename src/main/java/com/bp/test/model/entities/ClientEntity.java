package com.bp.test.model.entities;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;

import static lombok.AccessLevel.PRIVATE;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "client")
@FieldDefaults(level = PRIVATE)
@PrimaryKeyJoinColumn(name = "client_id")
public class ClientEntity extends PersonEntity{

    @Column(name = "password")
    String password;

    @Column(name = "status")
    Boolean status;
}
