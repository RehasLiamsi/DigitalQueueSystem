package com.example.queueProject.controller;

import com.example.queueProject.entity.Person;
import com.example.queueProject.repository.PersonRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/person")
public class PersonController {

    private final PersonRepository repository;

    public PersonController(PersonRepository repository) {
        this.repository = repository;
    }

    @PostMapping
    void addPerson(@RequestBody Person person) {
        repository.save(person);
    }

    @PutMapping("/{id}")
    ResponseEntity<Person> updatePerson(@PathVariable Long id, @RequestBody Person person) {
        Person personToUpdate = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        personToUpdate.setPositionInQueue(person.getPositionInQueue());
        personToUpdate.setLeftAtTime(person.getLeftAtTime());
        repository.save(personToUpdate);

        return ResponseEntity.ok(personToUpdate);
    }
}
