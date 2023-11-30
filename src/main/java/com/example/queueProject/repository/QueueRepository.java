package com.example.queueProject.repository;

import com.example.queueProject.entity.Queue;
import org.springframework.data.repository.ListCrudRepository;

import java.util.Optional;

public interface QueueRepository extends ListCrudRepository<Queue, Long> {

    Queue getQueueByQueueId(Long id);

    Queue getQueueByQueueName(String name);
}
