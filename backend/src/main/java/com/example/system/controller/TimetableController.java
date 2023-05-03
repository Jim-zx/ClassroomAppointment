package com.example.system.controller;

import com.example.system.domain.Timetable;
import com.example.system.dto.Week;
import com.example.system.service.TimetableService;
import com.example.system.utils.result.DataResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

@RestController
public class TimetableController {
    @Autowired
    private TimetableService timetableService;

    @PostMapping("/defaultTimetable")
    public DataResult<ArrayList<Week>> all(@RequestBody ArrayList<String> data) throws ParseException {
        String start=data.get(0);
        String end=data.get(1);
        Long classroom=Long.parseLong(data.get(2));
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        Date startTime=sdf.parse(start);
        Date endTime=sdf.parse(end);
        return DataResult.successByDatas(timetableService.createDate(startTime,endTime,classroom));
    }

    @PostMapping("/addActiveTime")
    public DataResult<Boolean> addActive(@RequestBody Timetable timetable, @RequestHeader("Authorization") String token){
        return DataResult.successByDatas(true);
    }

    @PostMapping("/addLessonTime")
    public DataResult<Boolean> addLesson(@RequestBody Timetable timetable, @RequestHeader("Authorization") String token){
        return DataResult.successByDatas(true);
    }

    @GetMapping("/allTimetable")
    public DataResult<ArrayList<Timetable>> getAll(){
        ArrayList<Timetable> result=timetableService.allTimetable();
//        for (Timetable timetable:result){
//            timetable.setStartDate(new Date(String.valueOf(timetable.getStartDate())));
//        }
        System.out.println(result.get(0).getStartDate());
        return DataResult.successByDatas(result);
    }

    @PostMapping("/addTimetable")
    public DataResult<Boolean> addTest(@RequestBody ArrayList<Timetable> timetables) throws ParseException {

//        Date start=new Date();
//        Date end;
//        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
//        String startTime=sdf.format(start);
//        String endTime=sdf.format(new Date(start.getTime()+3 * 24 * 60 * 60 * 1000));
//        start=sdf.parse(startTime);
//        end=sdf.parse(endTime);
//        System.out.println("The time:"+start);
//        System.out.println("The time:"+startTime);
//        System.out.println("The end time:"+end);
//        System.out.println("The end time:"+endTime);
//        ArrayList<Integer> arrayList=new ArrayList<>();
//        arrayList.add(1);
//        ArrayList<Integer> arrayList1=new ArrayList<>();
//        arrayList1.add(1);
//        Timetable test=new Timetable(1,1,-1,1,start,end,arrayList,arrayList1);
////        timetableService.addTimetables(test);
//        start=new Date(start.getTime()-24 * 60 * 60 * 1000);
//        end=new Date(end.getTime()+24 * 60 * 60 * 1000);
        return DataResult.successByDatas(timetableService.addTimetables(timetables));
    }
}
