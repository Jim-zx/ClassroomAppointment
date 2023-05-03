package com.example.system.service.impl;

import com.example.system.domain.Active;
import com.example.system.domain.Timetable;
import com.example.system.repository.ActiveRepository;
import com.example.system.service.ActiveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;

@Service
public class ActiveServiceImpl implements ActiveService {

    @Autowired
    private ActiveRepository activeRepository;


    @Override
    public ArrayList<Active> allActive() {
        return (ArrayList<Active>) activeRepository.findAll();
    }

    @Override
    public boolean addActive(ArrayList<Active> actives) {
        activeRepository.saveAll(actives);
        return true;
    }
}
