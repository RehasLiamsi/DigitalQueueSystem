package com.example.queueProject.repository;

import com.example.queueProject.entity.Queue;
import org.springframework.data.repository.ListCrudRepository;

public interface QueueRepository extends ListCrudRepository<Queue, Long> {

    Queue getQueueByQueueId(Long id);

    Queue getQueueByQueueName(String name);

    Queue getQueueByQueueStatus(Boolean status);
}
