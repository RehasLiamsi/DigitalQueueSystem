package com.example.queueProject.controller;

import com.example.queueProject.dto.PersonDto;
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
    ResponseEntity<PersonDto> addPerson(@RequestBody PersonDto personDto) {
        personDto.setJoinedAtTime(LocalDateTime.now());
        Person person = conversionService.convertToPersonEntity(personDto);
        personRepository.save(person);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(personDto.getPersonId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{id}")
    ResponseEntity<Person> updatePositionInQueue(@PathVariable Long id) {
        Person personToUpdate = personRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        Long oldPositionInQueue = getPositionInQueue(id);
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


    @GetMapping("/position/{id}")
    Long getPositionInQueue(@PathVariable long id) {
        Person personForInfo = personRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return personForInfo.getPositionInQueue();
    }


    @GetMapping
    List<Person> getPersons() {
        return personRepository.findAll();
    }

    @GetMapping("/{id}")
    PersonDto getPersonById(@PathVariable long id) {
        Person person = personRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return conversionService.convertToPersonDto(person);
    }

    @DeleteMapping("/{id}")
    void deletePerson(@PathVariable Long id) {
        if (personRepository.findById(id).isPresent())
            personRepository.deleteById(id);
        else
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }


}
