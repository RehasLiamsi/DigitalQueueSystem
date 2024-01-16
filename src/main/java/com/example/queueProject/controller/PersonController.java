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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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
    ResponseEntity<PersonOutputDetailDto> addPerson(@PathVariable Long queueId) {
        PersonInputDetailDto personInputDetailDto = new PersonInputDetailDto();
        personInputDetailDto.setJoinedAtTime(LocalDateTime.now());
        personInputDetailDto.setQueueId(queueId);
        var queue = queueRepository.getQueueByQueueId(queueId);
        if (queue.getQueueStatus()) {
            Person person = conversionService.convertToPersonEntity(personInputDetailDto);
            Long currentPositionInQueue = personRepository.countByQueueQueueIdAndLeftAtTimeIsNull(queue.getQueueId());
            person.setPositionInQueue((currentPositionInQueue + 1));
            personRepository.save(person);

            PersonOutputDetailDto personOutputDetailDto = conversionService.convertToPersonOutputDetailDto(person);

            URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                    .buildAndExpand(personInputDetailDto.getPersonId()).toUri();
            return ResponseEntity.created(location).body(personOutputDetailDto);

        } else {
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE);
        }


    }

    @PutMapping("/update/{id}")
    ResponseEntity<PersonOutputDetailDto> updatePositionInQueue(@PathVariable Long id) {
        Person personToUpdate = personRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        updateQueuePositions(personToUpdate);

        return ResponseEntity.ok(conversionService.convertToPersonOutputDetailDto(personToUpdate));
    }

    @PutMapping("/left/{id}")
    ResponseEntity<PersonOutputDetailDto> updateLeftAtTIme(@PathVariable Long id) {
        Person personLeaving = personRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        personLeaving.setLeftAtTime(LocalDateTime.now());
        personLeaving.setPositionInQueue(0L);
        personRepository.save(personLeaving);

        List<Person> personsToUpdate = personRepository.findAllByJoinedAtTimeAfterAndLeftAtTimeIsNull(personLeaving.getJoinedAtTime());
        for (Person person : personsToUpdate) {
            updateQueuePositions(person);
        }

        return ResponseEntity.ok(conversionService.convertToPersonOutputDetailDto(personLeaving));
    }

    @PutMapping("/left/all/{queueName}")
    ResponseEntity<PersonOutputDetailDto> dropAllPersonsFromQueue(@PathVariable String queueName) {
        List<Person> allActivePersonsInQueue = personRepository.findAllByQueue_QueueNameAndLeftAtTimeIsNull(queueName);
        for (Person person : allActivePersonsInQueue) {
            person.setLeftAtTime(LocalDateTime.now());
            personRepository.save(person);
        }
        return ResponseEntity.ok().build();
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

    @GetMapping("/activeQueue/first")
    ResponseEntity<PersonOutputDetailDto> getFirstPersonInActiveQueue() {
        Boolean queueStatus = true;
        Long positionInQueue = 1L;
        Person person = personRepository.findByQueue_QueueStatusAndPositionInQueue(queueStatus, positionInQueue);
        return ResponseEntity.ok(conversionService.convertToPersonOutputDetailDto(person));
    }

    @GetMapping("/queue/{queueName}")
    List<PersonOutputDetailDto> getPersonsByQueueName(@PathVariable String queueName) {
        List<Person> persons = personRepository.findAllByQueue_QueueName(queueName);
        return persons.stream()
                .map(conversionService::convertToPersonOutputDetailDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{personId}/position")
    Long getPositionInQueueByPersonId(@PathVariable Long personId) {
        Person person = personRepository.findById(personId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return person.getPositionInQueue();
    }

    @GetMapping("/count")
    Long getCountOfPersons() {
        return personRepository.countBy();
    }

    @GetMapping("/count/activeQueue")
    Long getCountOfPersonsInActiveQueue() {
        Boolean status = true;
        return personRepository.countByQueue_QueueStatusAndLeftAtTimeIsNull(status);
    }

    @GetMapping("/count/all/{queueId}")
    Long getTotalCountOfPersonsWhoJoinedQueueDuringTheDay(@PathVariable Long queueId) {
        LocalDate today = LocalDate.now();
        LocalDateTime startOfDay = today.atStartOfDay();
        LocalDateTime endOfDay = LocalDateTime.of(today, LocalTime.MAX);
        return personRepository.countByQueueQueueIdAndJoinedAtTimeBetween(queueId, startOfDay, endOfDay);
    }

    @DeleteMapping("/delete/{id}")
    void deletePerson(@PathVariable Long id) {
        if (personRepository.findById(id).isPresent())
            personRepository.deleteById(id);
        else
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

    private void updateQueuePositions(Person person) {
        Long oldPosition = person.getPositionInQueue();
        person.setPositionInQueue(--oldPosition);
        personRepository.save(person);
    }
}
