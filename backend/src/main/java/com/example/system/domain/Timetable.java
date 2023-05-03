package com.example.system.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;

@Entity
@Table(name = "timetable")
public class Timetable {
    @Id
    @Setter
    @Getter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Setter @Getter
    @Column(name = "room_id", nullable = false, length = 100)
    private Long roomId;

    @Setter @Getter
    @Column(name = "active_id", nullable = true, length = 100)
    private Long activeId;

    @Setter @Getter
    @Column(name = "lesson_id", nullable = true, length = 100)
    private Long lessonId;

    @Setter @Getter
    @Column(name = "start_date", nullable = true, length = 100)
    private Date startDate;


    @Setter @Getter
    @Column(name = "end_date", nullable = true, length = 100)
    private Date endDate;

    @Setter @Getter
    @Column(name = "day", nullable = true, length = 100)
    private ArrayList<Integer> day;

    @Setter @Getter
    @Column(name = "time", nullable = true, length = 100)
    private ArrayList<Integer> time;

    public Timetable() {
    }

    public Timetable(long roomId, long activeId,long lessonId, Date startDate, Date endDate, ArrayList<Integer> day, ArrayList<Integer> time) {
        this.id = id;
        this.roomId = roomId;
        this.activeId = activeId;
        this.lessonId = lessonId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.day = day;
        this.time = time;
    }


}
