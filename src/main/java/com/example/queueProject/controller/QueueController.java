package com.example.queueProject.controller;

import com.example.queueProject.dto.QueueDetailDto;
import com.example.queueProject.dto.QueueSummaryDto;
import com.example.queueProject.entity.Queue;
import com.example.queueProject.repository.QueueRepository;
import com.example.queueProject.services.ConversionService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin
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

    @PutMapping("/{id}")
    ResponseEntity<QueueDetailDto> updateQueueStatus(@PathVariable Long id) {
        Queue queueToUpdate = queueRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        queueToUpdate.setQueueStatus(!queueToUpdate.getQueueStatus());
        queueRepository.save(queueToUpdate);

        return ResponseEntity.ok(conversionService.convertToQueueDetailDTO(queueToUpdate));
    }

    @GetMapping("/active")
    public QueueSummaryDto getQueueByQueueStatus() {
        Boolean status = true;
        Queue queue = queueRepository.getQueueByQueueStatus(status);
        return conversionService.convertToQueueSummaryDTO(queue);
    }

    @GetMapping("/active/name")
    public String getQueueNameByQueueStatus() {
        Boolean status = true;
        Queue queue = queueRepository.getQueueByQueueStatus(status);
        return queue.getQueueName();
    }

    @GetMapping("/active/id")
    public Long getQueueIdByQueueStatus() {
        Boolean status = true;
        Queue queue = queueRepository.getQueueByQueueStatus(status);
        return queue.getQueueId();
    }
}
