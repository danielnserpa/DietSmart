package com.example.spring_first_app;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Users, String> {

    // Find users by email and password
    Users findByEmailAndPassword(String email, String password);
    // Find user by email
    Users findByEmail(String email);
}