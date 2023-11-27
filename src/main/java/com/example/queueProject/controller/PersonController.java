package com.example.queueProject.controller;

import com.example.queueProject.entity.Person;
import com.example.queueProject.repository.PersonRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

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

    @GetMapping("/{id}")
    Long getPositionInQueue(@PathVariable long id) {
        Person personForInfo = repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return personForInfo.getPositionInQueue();
    }


    @GetMapping
    List<Person> getPersons() {
        return repository.findAll();
    }

    @DeleteMapping("/{id}")
    void deletePerson(@PathVariable Long id){
        if (repository.findById(id).isPresent())
            repository.deleteById(id);
        else
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }
}
