package com.bp.test.model.entities;

import lombok.*;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_person", nullable = false)
    Long idPerson;

    @Column(name = "name")
    String name;

    @Column(name = "gender")
    String gender;

    @Column(name = "age")
    Long age;

    @Column(name = "identification", unique = true)
    String identification;

    @Column(name = "address")
    String address;

    @Column(name = "phone")
    String phone;
}
