package com.example.queueProject.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class PersonSummaryDto {
    private Long personId;
    private Long positionInQueue;
    private LocalDateTime joinedAtTime;
    private LocalDateTime leftAtTime;
}
