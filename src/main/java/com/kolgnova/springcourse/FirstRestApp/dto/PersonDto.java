package com.kolgnova.springcourse.FirstRestApp.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PersonDto {

    @NotEmpty(message = "Имя не должно быть пустым")
    @Size(min = 2, max = 100, message = "Имя должно быть от 2 до 100 символов длинной")
    private String name;
    @Min(value = 0, message = "Возраст должен быть больше, чем 0 лет")
    private Integer age;
    @Email
    @NotEmpty(message = "Мэил не должен быть пустым")
    private String email;

}
