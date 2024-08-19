package com.example.spring_first_app;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;


import java.time.LocalDate;


// Maps this class to the "USER" table in the database
@Entity
@Data
@Table(name = "USER")

public class Users {

    @Id
    @Column(name = "email")
    private String email;

    @Column(name = "firstName")
    private String firstName;

    @Column(name = "lastName")
    private String lastName;

    @Column(name = "password")
    private String password;

    @Column(name = "confirmPassword")
    private String confirmPassword;

    @Column(name = "firstLogin")
    private boolean firstLogin = true;

    @Column(name = "dob")
    private LocalDate dob;

    @Column(name = "sex")
    private String sex;

    @Column(name = "height")
    private Double height;

    @Column(name = "weight")
    private Double weight;

    @Column(name = "hip")
    private Double hip;

    @Column(name = "waist")
    private Double waist;

    @Column(name = "goal")
    private String goal;

    @Column(name = "pal")
    private String pal;

}
