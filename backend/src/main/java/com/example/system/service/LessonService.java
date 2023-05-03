package com.example.system.service;

import com.example.system.domain.Lesson;

import java.util.ArrayList;

public interface LessonService {
    ArrayList<Lesson> allLesson();

    boolean addLesson(ArrayList<Lesson> lessons);

    boolean deleteLesson(ArrayList<Lesson> lessons);

}
