package com.example.system.service;

import com.example.system.domain.Timetable;
import com.example.system.dto.Week;

import java.util.ArrayList;
import java.util.Date;

public interface TimetableService {
    ArrayList<Timetable> allTimetable();

    ArrayList<Week> createDate(Date start, Date end,Long classroomId);

    Boolean addTimetables(ArrayList<Timetable> timetables);

}
