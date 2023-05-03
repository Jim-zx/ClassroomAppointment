package com.example.system.service.impl;

import com.example.system.domain.Lesson;
import com.example.system.domain.Timetable;
import com.example.system.domain.User;
import com.example.system.repository.LessonRepository;
import com.example.system.repository.TimetableRepository;
import com.example.system.service.LessonService;
import com.example.system.utils.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class LessonServiceImpl implements LessonService {
    @Autowired
    private LessonRepository lessonRepository;
    @Autowired
    private TimetableRepository timetableRepository;

    @Override
    public ArrayList<Lesson> allLesson() {
        return (ArrayList<Lesson>) lessonRepository.findAll();
    }

    @Override
    public boolean addLesson(ArrayList<Lesson> lessons) {
        for (Lesson lesson : lessons) {
            Lesson repeat = lessonRepository.findByName(lesson.getName());
            if (repeat != null) {
                return false;
            }

        }
        lessonRepository.saveAll(lessons);
        return false;
    }

    @Override
    public boolean deleteLesson(ArrayList<Lesson> lessons) {
        List<Timetable> deleteTimetable=new ArrayList<>();
        for (Lesson lesson : lessons) {
            Optional<Lesson> repeat = lessonRepository.findById(lesson.getId());
            if (!repeat.isPresent()) {
                return false;
            }
        }
        for (Lesson lesson:lessons){
            deleteTimetable.addAll(timetableRepository.findByLessonId(lesson.getId()));
        }
        timetableRepository.deleteAll(deleteTimetable);
        lessonRepository.deleteAll(lessons);
        return true;
    }


}
