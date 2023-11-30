package com.example.queueProject.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class QueueDetailDto {
    private Long queueId;
    private String queueName;
    private Boolean queueStatus;
    private List<PersonSummaryDto> persons;
}
