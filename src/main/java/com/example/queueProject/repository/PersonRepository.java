package com.example.queueProject.repository;

import com.example.queueProject.entity.Person;
import org.springframework.data.repository.ListCrudRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface PersonRepository extends ListCrudRepository<Person, Long> {

    Optional<Person> findPersonByPositionInQueue(Long positionInQueue);

    List<Person> findAllByQueue_QueueName(String queueName);

    Long countByQueueQueueIdAndLeftAtTimeIsNull(Long queueId);

    Long countBy();

    Long countByQueue_QueueStatusAndLeftAtTimeIsNull(Boolean status);

    Long countByQueueQueueId(Long queueId);

    List<Person> findAllByJoinedAtTimeAfterAndLeftAtTimeIsNull(LocalDateTime joinedAtTime);
}
