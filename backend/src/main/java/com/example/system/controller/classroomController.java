package com.example.system.controller;

import com.example.system.domain.Classroom;
import com.example.system.service.ClassroomService;
import com.example.system.utils.result.DataResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
public class classroomController {
    @Autowired
    private ClassroomService classroomService;

    @GetMapping("/getAllClassroom")
    public DataResult<ArrayList<Classroom>> getAllClassroom(){
        return DataResult.successByDatas(classroomService.getAllClassroom());
    }

    @PostMapping("/addClassroom")
    public DataResult<Boolean> addClassroom(@RequestBody ArrayList<Classroom> classrooms){
        return DataResult.successByDatas(classroomService.addClassroom(classrooms));
    }

    @PostMapping("/deleteClassroom")
    public DataResult<Boolean> deleteClassroom(@RequestBody ArrayList<Classroom> classrooms){
        return DataResult.successByDatas(classroomService.deleteClassroom(classrooms));
    }
}
