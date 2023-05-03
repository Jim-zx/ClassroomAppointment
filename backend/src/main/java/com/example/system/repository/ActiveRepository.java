package com.example.system.repository;

import com.example.system.domain.Active;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;

public interface ActiveRepository extends JpaRepository<Active, Long> {
    ArrayList<Active> findByAccept(Boolean accept);
}
