package com.example.kampustesttask.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.OffsetDateTime;

@Data
@Entity
@Table(name = "reminder")
public class Reminder {

    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE, generator = "reminder_id_seq")
    @SequenceGenerator(name = "reminder_id_seq", allocationSize = 1, initialValue = 1000)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "dt_remind")
    private OffsetDateTime dtRemind;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}
