package com.example.queueProject.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Queue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="queue_id")
    private Long queueId;

    @Column(name="queue_name")
    String queueName;

    @Column(name="queue_status")
    Boolean queueStatus;
}
