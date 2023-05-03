package com.example.system.repository;

import com.example.system.domain.Timetable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;
import java.util.Date;

public interface TimetableRepository extends JpaRepository<Timetable,Long> {
    public ArrayList<Timetable> findByStartDateBeforeAndEndDateAfterAndRoomIdIs(Date startDate, Date endDate, Long classroom);

    public ArrayList<Timetable> findByLessonId(Long lessonId);

    public ArrayList<Timetable> findByRoomId(Long roomId);
}
