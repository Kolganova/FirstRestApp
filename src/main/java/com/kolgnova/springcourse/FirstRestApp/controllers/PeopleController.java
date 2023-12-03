package com.kolgnova.springcourse.FirstRestApp.controllers;

import com.kolgnova.springcourse.FirstRestApp.dto.PersonDto;
import com.kolgnova.springcourse.FirstRestApp.models.Person;
import com.kolgnova.springcourse.FirstRestApp.services.PeopleService;
import com.kolgnova.springcourse.FirstRestApp.util.PersonErrorResponse;
import com.kolgnova.springcourse.FirstRestApp.util.PersonNotCreatedException;
import com.kolgnova.springcourse.FirstRestApp.util.PersonNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
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
    private final ModelMapper modelMapper;

    @GetMapping
    public List<PersonDto> getPeople() {
        return peopleService.findAll().stream()
                .map(this::convertToPersonDto)
                .toList();
    }

    @GetMapping("/{id}")
    public PersonDto getPerson(@PathVariable("id") int id) {
        return convertToPersonDto(peopleService.findOne(id));
    }

    @PostMapping
    public ResponseEntity<HttpStatus> create(@RequestBody @Valid PersonDto personDto,
                                             BindingResult bindingResult) {
        if ((bindingResult.hasErrors())) {
            StringBuilder errorMessage = new StringBuilder();

            List<FieldError> errors = bindingResult.getFieldErrors();
            errors.stream()
                    .map(f -> f.getField() + " - " + f.getDefaultMessage() + ";")
                    .forEach(errorMessage::append);

            throw new PersonNotCreatedException(errorMessage.toString());
        }

        peopleService.save(convertToPerson(personDto));

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

    private Person convertToPerson(PersonDto personDto) {
        return modelMapper.map(personDto, Person.class);
    }

    private PersonDto convertToPersonDto(Person person) {
        return modelMapper.map(person, PersonDto.class);
    }

}
