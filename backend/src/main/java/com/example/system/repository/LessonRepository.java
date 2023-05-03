package com.example.system.repository;

import com.example.system.domain.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LessonRepository extends JpaRepository<Lesson,Long> {
    public Lesson findByName(String name);
}
