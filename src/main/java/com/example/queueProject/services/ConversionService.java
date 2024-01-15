package com.example.queueProject.services;

import com.example.queueProject.dto.*;
import com.example.queueProject.entity.Person;
import com.example.queueProject.entity.Queue;
import com.example.queueProject.repository.PersonRepository;
import com.example.queueProject.repository.QueueRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class ConversionService {

    private final PersonRepository personRepository;
    private final QueueRepository queueRepository;

    public ConversionService(PersonRepository personRepository, QueueRepository queueRepository) {
        this.personRepository = personRepository;
        this.queueRepository = queueRepository;
    }

    public PersonOutputDetailDto convertToPersonOutputDetailDto(Person person) {
        PersonOutputDetailDto dto = new PersonOutputDetailDto();
        dto.setPersonId(person.getPersonId());
        dto.setPositionInQueue(person.getPositionInQueue());
        dto.setJoinedAtTime(person.getJoinedAtTime());
        dto.setLeftAtTime(person.getLeftAtTime());

        if (person.getQueue() != null) {
        dto.setQueueName(person.getQueue().getQueueName());}
        else {
            dto.setQueueName("No connected Queue");
        }
        return dto;
    }

    public Person convertToPersonEntity(PersonInputDetailDto dto) {
        Person person = new Person();
        if (dto.getPersonId() != null) {
            person = personRepository.findById(dto.getPersonId())
                    .orElseThrow(() -> new EntityNotFoundException("Person not found"));
        }
        person.setPositionInQueue(dto.getPositionInQueue());
        person.setJoinedAtTime(dto.getJoinedAtTime());
        person.setLeftAtTime(dto.getLeftAtTime());
        person.setQueue(queueRepository.getQueueByQueueId(dto.getQueueId()));
        return person;
    }

    public QueueDetailDto convertToQueueDetailDTO(Queue queue) {
        QueueDetailDto dto = new QueueDetailDto();
        dto.setQueueId(queue.getQueueId());
        dto.setQueueName(queue.getQueueName());
        dto.setQueueStatus(queue.getQueueStatus());
        dto.setPersons(queue.getPersons().stream()
                .map(this::convertToPersonSummaryDto)
                .collect(Collectors.toList()));
        return dto;
    }


    public QueueSummaryDto convertToQueueSummaryDTO(Queue queue) {
        QueueSummaryDto dto = new QueueSummaryDto();
        dto.setQueueId(queue.getQueueId());
        dto.setQueueName(queue.getQueueName());
        dto.setQueueStatus(queue.getQueueStatus());
        return dto;
    }

    public PersonSummaryDto convertToPersonSummaryDto(Person person) {
        PersonSummaryDto dto = new PersonSummaryDto();
        dto.setPersonId(person.getPersonId());
        dto.setPositionInQueue(person.getPositionInQueue());
        dto.setJoinedAtTime(person.getJoinedAtTime());
        dto.setLeftAtTime(person.getLeftAtTime());
        return dto;
    }
}
