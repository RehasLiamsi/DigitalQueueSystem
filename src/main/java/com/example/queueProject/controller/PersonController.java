package com.example.queueProject.controller;

import com.example.queueProject.dto.PersonDto;
import com.example.queueProject.entity.Person;
import com.example.queueProject.entity.Queue;
import com.example.queueProject.repository.PersonRepository;
import com.example.queueProject.repository.QueueRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/person")
public class PersonController {

    private final PersonRepository personRepository;
    private final QueueRepository queueRepository;

    public PersonController(PersonRepository personRepository, QueueRepository queueRepository) {
        this.personRepository = personRepository;
        this.queueRepository = queueRepository;
    }

    @PostMapping
    public PersonDto addPerson(@RequestBody PersonDto personDto) {
        Person person = convertToEntity(personDto);
        person = personRepository.save(person);
        return personDto;
    }

    @PutMapping("/{id}")
    ResponseEntity<Person> updatePerson(@PathVariable Long id, @RequestBody Person person) {
        Person personToUpdate = personRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        personToUpdate.setPositionInQueue(person.getPositionInQueue());
        personToUpdate.setLeftAtTime(person.getLeftAtTime());
        personRepository.save(personToUpdate);

        return ResponseEntity.ok(personToUpdate);
    }

    @GetMapping("/{id}")
    Long getPositionInQueue(@PathVariable long id) {
        Person personForInfo = personRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return personForInfo.getPositionInQueue();
    }


    @GetMapping
    List<Person> getPersons() {
        return personRepository.findAll();
    }

    @DeleteMapping("/{id}")
    void deletePerson(@PathVariable Long id) {
        if (personRepository.findById(id).isPresent())
            personRepository.deleteById(id);
        else
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }


    private PersonDto convertToDTO(Person person) {
        PersonDto dto = new PersonDto();
        dto.setPersonId(person.getPersonId());
        dto.setPositionInQueue(person.getPositionInQueue());
        dto.setJoinedAtTime(person.getJoinedAtTime());
        dto.setLeftAtTime(person.getLeftAtTime());
        dto.setQueueId(person.getQueue() != null ? person.getQueue().getQueueId() : null);
        return dto;
    }

    private Person convertToEntity(PersonDto dto) {
        Person person = new Person();
        if (dto.getPersonId() != null) {
            person = personRepository.findById(dto.getPersonId())
                    .orElseThrow(() -> new EntityNotFoundException("Person not found"));
        }
        person.setPositionInQueue(dto.getPositionInQueue());
        person.setJoinedAtTime(dto.getJoinedAtTime());
        person.setLeftAtTime(dto.getLeftAtTime());
        if (dto.getQueueId() != null) {
            Queue queue = queueRepository.findById(dto.getQueueId())
                    .orElseThrow(() -> new EntityNotFoundException("Queue not found"));
            person.setQueue(queue);
        }
        return person;
    }
}
