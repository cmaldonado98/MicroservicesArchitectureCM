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
@Table(name = "client")
@FieldDefaults(level = PRIVATE)
public class ClientEntity extends PersonEntity{

    @Column(name = "password")
    String password;

    @Column(name = "state")
    Boolean state;
}
