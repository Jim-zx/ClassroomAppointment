package com.example.system.controller;

import com.example.system.domain.Lesson;
import com.example.system.service.LessonService;
import com.example.system.utils.result.DataResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
public class lessonController {
    @Autowired
    private LessonService lessonService;

    @GetMapping("/getAllLesson")
    public DataResult<ArrayList<Lesson>> getAll(){
        return DataResult.successByDatas(lessonService.allLesson());
    }

    @PostMapping("/deleteLesson")
    public DataResult<Boolean> deleteLesson(@RequestBody ArrayList<Lesson> lessons){
        return DataResult.successByDatas(lessonService.deleteLesson(lessons));
    }

    @PostMapping("/addLesson")
    public DataResult<Boolean> addLesson(@RequestBody ArrayList<Lesson> lessons){
        return DataResult.successByDatas(lessonService.addLesson(lessons));
    }
}
