package com.kolgnova.springcourse.FirstRestApp.controllers;

import com.kolgnova.springcourse.FirstRestApp.models.Person;
import com.kolgnova.springcourse.FirstRestApp.services.PeopleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/people")
@RequiredArgsConstructor
public class PeopleController {

    private final PeopleService peopleService;

    @GetMapping
    public List<Person> getPeople() {
        return peopleService.findAll();
    }
    @GetMapping("/{id}")
    public Person getPerson(@PathVariable("id") int id) {
        return peopleService.findOne(id);
    }

}
