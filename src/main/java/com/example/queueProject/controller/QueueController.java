package com.example.queueProject.controller;

import com.example.queueProject.entity.Queue;
import com.example.queueProject.repository.QueueRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/queue")
public class QueueController {

    private final QueueRepository repository;

    public QueueController(QueueRepository repository) {
        this.repository = repository;
    }

    @PostMapping
    void addQueue(@RequestBody Queue queue) {
        repository.save(queue);
    }

    @GetMapping
    List<Queue> getAllQueues() {
        return repository.findAll();
    }
}
