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
    @Column(name="person_id")
    private Long personId;

    @Column(name="position_in_queue")
    Long positionInQueue;

    @Column(name="joined_at_time")
    LocalDateTime joinedAtTime;

    @Column(name="left_at_time")
    LocalDateTime leftAtTime;

    @ManyToOne
    @JoinColumn(name="person_queue_idfk")
    private Queue queue;
}
