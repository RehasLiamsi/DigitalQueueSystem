package com.example.queueProject.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class QueueSummaryDto {
    private Long queueId;
    private String queueName;
    private Boolean queueStatus;
}
