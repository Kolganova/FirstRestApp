package com.kolgnova.springcourse.FirstRestApp.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity
@Table(name = "Person")
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class Person {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotEmpty(message = "Имя не должно быть пустым")
    @Size(min = 2, max = 100, message = "Имя должно быть от 2 до 100 символов длинной")
    @Column(name = "name")
    private String name;
    @Min(value = 0, message = "Возраст должен быть больше, чем 0 лет")
    @Column(name = "age")
    private Integer age;
    @Column(name = "email")
    @Email
    @NotEmpty(message = "Мэил не должен быть пустым")
    private String email;

}
