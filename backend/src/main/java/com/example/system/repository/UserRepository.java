package com.example.system.repository;

import com.example.system.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmailAndPassword(String email,String password);

    User findByEmail(String email);
}