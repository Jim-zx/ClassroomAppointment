package com.example.system.controller;

import com.example.system.domain.Active;
import com.example.system.domain.Timetable;
import com.example.system.service.ActiveService;
import com.example.system.service.TimetableService;
import com.example.system.utils.result.DataResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@RestController
public class activeController {
    @Autowired
    private ActiveService activeService;
    @Autowired
    private TimetableService timetableService;

    @GetMapping("/allActive")
    public DataResult<ArrayList<Active>> all(){
        return DataResult.successByDatas(activeService.allActive());
    }

    @PostMapping("/addActive")
    public DataResult<Boolean> addActive(@RequestBody ArrayList<Active> actives){
        Calendar calendar=Calendar.getInstance();
        if (activeService.addActive(actives)){
            ArrayList<Timetable> timetables=new ArrayList<>();
            for (Active active:actives) {
                Date start,end;
                start=new Date(active.getDate().getTime()-4 * 24 * 60 * 60 * 1000);
                end=new Date(active.getDate().getTime()+4 * 24 * 60 * 60 * 1000);
                Timetable timetable=new Timetable();
                timetables.add(timetable);
                timetable.setLessonId((long)-1);
                timetable.setActiveId(active.getId());
                timetable.setStartDate(start);
                timetable.setEndDate(end);
                timetable.setTime(active.getTime());
                timetable.setRoomId(active.getRoomId());
                calendar.setTime(active.getDate());
                ArrayList<Integer> temp=new ArrayList<>();
                temp.add(calendar.get(Calendar.DAY_OF_WEEK));
                timetable.setDay(temp);
            }
            return DataResult.successByDatas(timetableService.addTimetables(timetables));
        }
        return DataResult.err();
    }
}
