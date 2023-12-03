package com.kolgnova.springcourse.FirstRestApp.services;

import com.kolgnova.springcourse.FirstRestApp.models.Person;
import com.kolgnova.springcourse.FirstRestApp.repositories.PeopleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PeopleService {

    private final PeopleRepository peopleRepository;

    public List<Person> findAll() {
        return peopleRepository.findAll();
    }

    public Person findOne(int id) {
        return peopleRepository.findById(id).orElse(null);
    }

}
