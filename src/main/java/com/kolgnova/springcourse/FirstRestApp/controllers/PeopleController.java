package com.kolgnova.springcourse.FirstRestApp.controllers;

import com.kolgnova.springcourse.FirstRestApp.models.Person;
import com.kolgnova.springcourse.FirstRestApp.services.PeopleService;
import com.kolgnova.springcourse.FirstRestApp.util.PersonErrorResponse;
import com.kolgnova.springcourse.FirstRestApp.util.PersonNotCreatedException;
import com.kolgnova.springcourse.FirstRestApp.util.PersonNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping
    public ResponseEntity<HttpStatus> create(@RequestBody @Valid Person person,
                                             BindingResult bindingResult) {
        if ((bindingResult.hasErrors())) {
            StringBuilder errorMessage = new StringBuilder();

            List<FieldError> errors = bindingResult.getFieldErrors();
            errors.stream()
                    .map(f -> f.getField() + " - " + f.getDefaultMessage() + ";")
                    .forEach(errorMessage::append);

            throw new PersonNotCreatedException(errorMessage.toString());
        }

        peopleService.save(person);

        return ResponseEntity.ok(HttpStatus.CREATED);
    }

    @ExceptionHandler
    private ResponseEntity<PersonErrorResponse> handleException(PersonNotFoundException e) {
        PersonErrorResponse response = new PersonErrorResponse(
                "Person with this id was not found!",
                System.currentTimeMillis()
        );

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    private ResponseEntity<PersonErrorResponse> handleException(PersonNotCreatedException e) {
        PersonErrorResponse response = new PersonErrorResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

}
