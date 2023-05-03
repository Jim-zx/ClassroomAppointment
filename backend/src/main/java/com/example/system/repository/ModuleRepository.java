package com.example.system.repository;

import com.example.system.domain.Module;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;

public interface ModuleRepository extends JpaRepository<Module,Long> {
    ArrayList<Module> findAllByAuthority(String auth);
}
