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
@Inheritance(strategy = InheritanceType.JOINED)
@Entity
@Table(name = "person")
@FieldDefaults(level = PRIVATE)
public class PersonEntity {

    @Id
    @Column(name = "id_person", nullable = false)
    Long idPerson;

    @Column(name = "name")
    String name;

    @Column(name = "gender")
    String gender;

    @Column(name = "age")
    Long age;

    @Column(name = "identification")
    String identification;

    @Column(name = "address")
    String address;

    @Column(name = "phone")
    String phone;
}
