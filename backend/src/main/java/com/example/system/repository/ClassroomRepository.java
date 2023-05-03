package com.example.system.repository;

import com.example.system.domain.Classroom;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ClassroomRepository extends JpaRepository<Classroom,Long> {
    Classroom findByTowerAndNum(String tower,String num);
}
