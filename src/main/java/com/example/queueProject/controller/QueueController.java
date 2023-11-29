package com.example.queueProject.controller;

import com.example.queueProject.dto.QueueDetailDto;
import com.example.queueProject.dto.QueueSummaryDto;
import com.example.queueProject.entity.Queue;
import com.example.queueProject.repository.QueueRepository;
import com.example.queueProject.services.ConversionService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/queue")
public class QueueController {

    private final QueueRepository queueRepository;
    private final ConversionService conversionService;

    public QueueController(QueueRepository queueRepository, ConversionService conversionService) {
        this.queueRepository = queueRepository;
        this.conversionService = conversionService;
    }

    @PostMapping
    void addQueue(@RequestBody Queue queue) {
        queueRepository.save(queue);
    }

    @GetMapping
    List<QueueSummaryDto> getAllQueues() {
        List<Queue> queues = queueRepository.findAll();
        return queues.stream()
                .map(conversionService::convertToQueueSummaryDTO)
                .collect(Collectors.toList());
    }


    @GetMapping("/{id}")
    public QueueDetailDto getQueueById(@PathVariable Long id) {
        Queue queue = queueRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Queue not found"));
        return conversionService.convertToQueueDetailDTO(queue);
    }
}
