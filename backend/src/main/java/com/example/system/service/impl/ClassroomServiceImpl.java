package com.example.system.service.impl;

import com.example.system.domain.Classroom;
import com.example.system.domain.Module;
import com.example.system.domain.Timetable;
import com.example.system.repository.ClassroomRepository;
import com.example.system.repository.TimetableRepository;
import com.example.system.service.ClassroomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class ClassroomServiceImpl implements ClassroomService {
    @Autowired
    private ClassroomRepository classroomRepository;
    @Autowired
    private TimetableRepository timetableRepository;


    @Override
    public ArrayList<Classroom> getAllClassroom() {
        return (ArrayList<Classroom>) classroomRepository.findAll();
    }

    @Override
    public boolean deleteClassroom(ArrayList<Classroom> classrooms) {
        ArrayList<Timetable> deleteTimetable=new ArrayList<>();
        for (Classroom classroom : classrooms) {
            Classroom repeat= classroomRepository.findByTowerAndNum(classroom.getTower(),classroom.getNum());
            if (repeat==null){
                return false;
            }
        }
        for(Classroom classroom :classrooms){
            deleteTimetable.addAll(timetableRepository.findByRoomId(classroom.getId()));
        }
        timetableRepository.deleteAll(deleteTimetable);
        classroomRepository.deleteAll(classrooms);
        return true;
    }

    @Override
    public boolean addClassroom(ArrayList<Classroom> classrooms) {
        for (Classroom classroom : classrooms) {
            Classroom repeat= classroomRepository.findByTowerAndNum(classroom.getTower(),classroom.getNum());
            if (repeat!=null){
                return false;
            }
        }
        classroomRepository.saveAll(classrooms);
        return true;
    }
}
