package com.example.queueProject.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long personId;
    Long positionInQueue;
    LocalDateTime joinedAtTime;
    LocalDateTime leftAtTime;

    @ManyToOne
    @JoinColumn(name = "personQueueIdFK")
    private Queue queueId;
}
