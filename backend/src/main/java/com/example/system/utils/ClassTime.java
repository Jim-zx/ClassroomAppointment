package com.example.system.utils;

import java.util.ArrayList;

public class ClassTime {
    public static ArrayList<String> classTime(){
        ArrayList<String> classTime=new ArrayList<>();
        classTime.add("08:10-08:55");
        classTime.add("09:00-09:45");
        classTime.add("10:15-11:00");
        classTime.add("11:05-11:50");
        classTime.add("");
        classTime.add("14:30-15:15");
        classTime.add("15:20-16:05");
        classTime.add("16:25-17:10");
        classTime.add("17:15-18:00");
        classTime.add("19:10-19:55");
        classTime.add("20:00-20:45");
        classTime.add("20:50-21:35");
//      ["08:10-08:55","09:00-09:45","10:15-11:00","11:05-11:50",
//      "","14:30-15:15","15:20-16:05","16:25-17:10",
//      "17:15-18:00","19:10-19:55","20:00-20:45","20:50-21:35"];
        return classTime;
    }
}
