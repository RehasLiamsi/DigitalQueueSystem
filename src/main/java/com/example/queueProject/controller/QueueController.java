package com.example.queueProject.controller;

import com.example.queueProject.entity.Queue;
import com.example.queueProject.repository.QueueRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
