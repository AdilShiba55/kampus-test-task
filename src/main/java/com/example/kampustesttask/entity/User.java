package com.example.kampustesttask.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.OffsetDateTime;

@Data
@Entity
@Table(name = "user", schema = "public")
public class User {

    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE, generator = "user_id_seq")
    @SequenceGenerator(name = "user_id_seq", allocationSize = 1, initialValue = 1000)
    private Long id;

    @Column(name = "email")
    private String email;

    @Column(name = "telegram")
    private String telegram;

    @Column(name = "dt_create")
    private OffsetDateTime dtCreate;

}
