package com.example.queueProject.controller;

import com.example.queueProject.dto.PersonInputDetailDto;
import com.example.queueProject.dto.PersonOutputDetailDto;
import com.example.queueProject.entity.Person;
import com.example.queueProject.repository.PersonRepository;
import com.example.queueProject.repository.QueueRepository;
import com.example.queueProject.services.ConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping("/person")
public class PersonController {

    private final PersonRepository personRepository;
    private final ConversionService conversionService;
    private final QueueRepository queueRepository;

    public PersonController(PersonRepository personRepository, ConversionService conversionService, QueueRepository queueRepository) {
        this.personRepository = personRepository;
        this.conversionService = conversionService;
        this.queueRepository = queueRepository;
    }

    @PostMapping("/add/{queueId}")
    ResponseEntity<PersonInputDetailDto> addPerson(@PathVariable Long queueId) {
        PersonInputDetailDto personInputDetailDto = new PersonInputDetailDto();
        personInputDetailDto.setJoinedAtTime(LocalDateTime.now());
        personInputDetailDto.setQueueId(queueId);
        var queue = queueRepository.getQueueByQueueId(queueId);
        if (queue.getQueueStatus()) {
            Person person = conversionService.convertToPersonEntity(personInputDetailDto);
            Long currentPositionInQueue = personRepository.countByQueueQueueIdAndLeftAtTimeIsNull(queue.getQueueId());
            person.setPositionInQueue((currentPositionInQueue + 1));
            personRepository.save(person);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE);
        }

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(personInputDetailDto.getPersonId()).toUri();
        return ResponseEntity.created(location).build();
    }

    /*@PostMapping
    ResponseEntity<PersonInputDetailDto> addPerson(@RequestBody PersonInputDetailDto personInputDetailDto) {
        personInputDetailDto.setJoinedAtTime(LocalDateTime.now());
        var queue = queueRepository.getQueueByQueueId(personInputDetailDto.getQueueId());
        if (queue.getQueueStatus()) {
            Person person = conversionService.convertToPersonEntity(personInputDetailDto);
            Long currentPositionInQueue = personRepository.countByQueueQueueIdAndLeftAtTimeIsNull(queue.getQueueId());
            person.setPositionInQueue((currentPositionInQueue + 1));
            personRepository.save(person);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE);
        }

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(personInputDetailDto.getPersonId()).toUri();
        return ResponseEntity.created(location).build();
    }*/

    @PutMapping("/update/{id}")
    ResponseEntity<PersonOutputDetailDto> updatePositionInQueue(@PathVariable Long id) {
        Person personToUpdate = personRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        Long oldPositionInQueue = personToUpdate.getPositionInQueue();
        personToUpdate.setPositionInQueue(--oldPositionInQueue);
        personRepository.save(personToUpdate);

        return ResponseEntity.ok(conversionService.convertToPersonOutputDetailDto(personToUpdate));
    }

    @PutMapping("/left/{id}")
    ResponseEntity<PersonOutputDetailDto> updateLeftAtTIme(@PathVariable Long id) {
        Person personToUpdate = personRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        personToUpdate.setLeftAtTime(LocalDateTime.now());
        personRepository.save(personToUpdate);

        return ResponseEntity.ok(conversionService.convertToPersonOutputDetailDto(personToUpdate));
    }

    @GetMapping
    List<PersonOutputDetailDto> getPersons() {
        List<Person> persons = personRepository.findAll();
        return persons.stream()
                .map(conversionService::convertToPersonOutputDetailDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    PersonOutputDetailDto getPersonById(@PathVariable long id) {
        Person person = personRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return conversionService.convertToPersonOutputDetailDto(person);
    }

    @GetMapping("/position/{position}")
    PersonOutputDetailDto getPersonByPositionInQueue(@PathVariable long position) {
        Person person = personRepository.findPersonByPositionInQueue(position)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return conversionService.convertToPersonOutputDetailDto(person);
    }

    @GetMapping("/queue/{queueName}")
    List<PersonOutputDetailDto> getPersonsByQueueName(@PathVariable String queueName) {
        List<Person> persons = personRepository.findAllByQueue_QueueName(queueName);
        return persons.stream()
                .map(conversionService::convertToPersonOutputDetailDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/count")
    Long getCountOfPersons(){
        return personRepository.countBy();
    }

    @GetMapping("/count/activeQueue")
    Long getCountOfPersonsInActiveQueue(){
        Boolean status = true;
        return personRepository.countByQueue_QueueStatusAndLeftAtTimeIsNull(status);
    }

    @GetMapping("/count/{queueId}")
    Long getTotalCountOfPersonsInQueue(@PathVariable Long queueId){
        return personRepository.countByQueueQueueId(queueId);
    }

    @DeleteMapping("/delete/{id}")
    void deletePerson(@PathVariable Long id) {
        if (personRepository.findById(id).isPresent())
            personRepository.deleteById(id);
        else
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }


}
