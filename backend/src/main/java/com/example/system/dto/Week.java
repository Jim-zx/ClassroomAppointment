package com.example.system.dto;

public class Week {
    private String time;
    private Day mon;
    private Day tue;
    private Day wed;
    private Day thu;
    private Day fri;
    private Day sat;
    private Day sun;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Day getMon() {
        return mon;
    }

    public void setMon(Day mon) {
        this.mon = mon;
    }

    public Day getTue() {
        return tue;
    }

    public void setTue(Day tue) {
        this.tue = tue;
    }

    public Day getWed() {
        return wed;
    }

    public void setWed(Day wed) {
        this.wed = wed;
    }

    public Day getThu() {
        return thu;
    }

    public void setThu(Day thu) {
        this.thu = thu;
    }

    public Day getFri() {
        return fri;
    }

    public void setFri(Day fri) {
        this.fri = fri;
    }

    public Day getSat() {
        return sat;
    }

    public void setSat(Day sat) {
        this.sat = sat;
    }

    public Day getSun() {
        return sun;
    }

    public void setSun(Day sun) {
        this.sun = sun;
    }

    public Week() {
        this.mon = new Day();
        this.tue = new Day();
        this.wed = new Day();
        this.thu = new Day();
        this.fri = new Day();
        this.sat = new Day();
        this.sun = new Day();
    }

    public Week(String time, Day mon, Day tue, Day wed, Day thu, Day fri, Day sat, Day sun) {
        this.time = time;
        this.mon = mon;
        this.tue = tue;
        this.wed = wed;
        this.thu = thu;
        this.fri = fri;
        this.sat = sat;
        this.sun = sun;
    }

    public Day getDay(int day){
        if (day==1){
            return this.mon;
        }else if (day==2){
            return this.tue;
        }else if (day==3){
            return this.wed;
        }else if (day==4){
            return this.thu;
        }else if (day==5){
            return this.fri;
        }else if (day==6){
            return this.sat;
        } else if (day == 7) {
            return this.sun;
        }else {
            return new Day();
        }
    }
}
