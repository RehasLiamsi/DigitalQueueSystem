package com.example.queueProject.controller;

import com.example.queueProject.dto.PersonInputDetailDto;
import com.example.queueProject.dto.PersonOutputDetailDto;
import com.example.queueProject.entity.Person;
import com.example.queueProject.repository.PersonRepository;
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

@RestController
@RequestMapping("/person")
public class PersonController {

    private final PersonRepository personRepository;
    private final ConversionService conversionService;

    public PersonController(PersonRepository personRepository, ConversionService conversionService) {
        this.personRepository = personRepository;
        this.conversionService = conversionService;
    }

    @PostMapping
    ResponseEntity<PersonInputDetailDto> addPerson(@RequestBody PersonInputDetailDto personInputDetailDto) {
        personInputDetailDto.setJoinedAtTime(LocalDateTime.now());
        Person person = conversionService.convertToPersonEntity(personInputDetailDto);
        personRepository.save(person);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(personInputDetailDto.getPersonId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{id}")
    ResponseEntity<Person> updatePositionInQueue(@PathVariable Long id) {
        Person personToUpdate = personRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        Long oldPositionInQueue = personToUpdate.getPositionInQueue();
        Long newPositionInQueue = --oldPositionInQueue;
        personToUpdate.setPositionInQueue(newPositionInQueue);
        personRepository.save(personToUpdate);

        return ResponseEntity.ok(personToUpdate);
    }

    @PutMapping("/left/{id}")
    ResponseEntity<Person> updateLeftAtTIme(@PathVariable Long id) {
        Person personToUpdate = personRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        personToUpdate.setLeftAtTime(LocalDateTime.now());
        personRepository.save(personToUpdate);

        return ResponseEntity.ok(personToUpdate);
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

    @DeleteMapping("/{id}")
    void deletePerson(@PathVariable Long id) {
        if (personRepository.findById(id).isPresent())
            personRepository.deleteById(id);
        else
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }


}
