package com.example.queueProject.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class PersonDto {

    private Long personId;
    private Long positionInQueue;
    private LocalDateTime joinedAtTime;
    private LocalDateTime leftAtTime;
    private Long queueId;

}
