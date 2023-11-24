package com.example.queueProject.repository;

import com.example.queueProject.entity.Person;
import org.springframework.data.repository.ListCrudRepository;

public interface PersonRepository extends ListCrudRepository<Person, Long> {
}
