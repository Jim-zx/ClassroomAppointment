package com.example.system.service;

import com.example.system.domain.Classroom;

import java.util.ArrayList;

public interface ClassroomService {
    public ArrayList<Classroom> getAllClassroom();

    public boolean addClassroom(ArrayList<Classroom> classrooms);

    public boolean deleteClassroom(ArrayList<Classroom> classrooms);
}
