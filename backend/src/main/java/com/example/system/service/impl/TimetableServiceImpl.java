package com.example.system.service.impl;

import com.example.system.domain.Active;
import com.example.system.domain.Classroom;
import com.example.system.domain.Timetable;
import com.example.system.dto.Day;
import com.example.system.dto.Week;
import com.example.system.repository.*;
import com.example.system.service.TimetableService;
import com.example.system.utils.ClassTime;
import com.example.system.utils.ColorList;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Service
public class TimetableServiceImpl implements TimetableService {
    @Resource
    private TimetableRepository timetableRepository;
    @Resource
    private LessonRepository lessonRepository;
    @Resource
    private ClassroomRepository classroomRepository;

    @Resource
    private ActiveRepository activeRepository;
    @Resource
    private UserRepository userRepository;
    @Override
    public ArrayList<Timetable> allTimetable() {
        return (ArrayList<Timetable>) timetableRepository.findAll();
    }

    @Override
    public ArrayList<Week> createDate(Date start, Date end, Long classroomId) {
        ArrayList<String> classTime = ClassTime.classTime();
        ArrayList<Week> weekList = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            weekList.add(new Week());
            weekList.get(i).setTime(classTime.get(i));
        }
        ArrayList<Timetable> scopeDate = timetableRepository.findByStartDateBeforeAndEndDateAfterAndRoomIdIs(start, end, classroomId);
        ArrayList<String> color = ColorList.colorful();
        for (Timetable timetable : scopeDate) {
            ArrayList<Integer> time = timetable.getTime();
            //改为只能一天
            int dayIndex = timetable.getDay().get(0);
            for (Integer integer : time) {
                Week week = weekList.get(integer);
                Day day = week.getDay(dayIndex);
                day.setPrincipal("Teacher");
                if (timetable.getActiveId() < 0) {
                    day.setName(lessonRepository.findById(timetable.getLessonId()).get().getName());
                } else {
                    Active active=activeRepository.findById(timetable.getActiveId()).get();
                    day.setName(active.getName());
                    day.setPrincipal(userRepository.findById(active.getPrincipalId()).get().getUsername());
                }
                day.setColor(color.remove((int) (Math.random() * color.size())));
                Classroom classroom = classroomRepository.findById(timetable.getRoomId()).get();
                day.setClassroom(classroom.getTower() + classroom.getNum());
            }
        }
        return weekList;
    }

    @Override
    public Boolean addTimetables(ArrayList<Timetable> timetables) {
        for (Timetable timetable : timetables) {
            ArrayList<Timetable> temp = timetableRepository.findByStartDateBeforeAndEndDateAfterAndRoomIdIs(timetable.getStartDate(), timetable.getEndDate(), timetable.getRoomId());
            for (Timetable existTimetable : temp) {
                if (existTimetable.getDay().get(0).equals(timetable.getDay().get(0))) {
                    ArrayList<Integer> classTime = timetable.getTime(), existClassTime = existTimetable.getTime();
                    for (Integer tep : existClassTime) {
                        if (classTime.contains(tep)) {
                            return false;
                        }
                    }
                }
            }
        }
        timetableRepository.saveAll(timetables);
        return true;
    }


}
