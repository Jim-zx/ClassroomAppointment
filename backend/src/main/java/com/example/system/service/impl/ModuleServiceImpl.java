package com.example.system.service.impl;

import com.example.system.domain.Module;
import com.example.system.repository.ModuleRepository;
import com.example.system.service.ModuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class ModuleServiceImpl implements ModuleService {
    @Autowired
    private ModuleRepository moduleRepository;
    @Override
    public ArrayList<Module> getModule(String auth) {
        String res="1";
        if (auth.equals("manage")){
            res="2";
        } else if (auth.equals("student")) {
            res="3";
        }
        return moduleRepository.findAllByAuthority(res);
    }

    @Override
    public ArrayList<Module> getAllModule() {
        return (ArrayList<Module>) moduleRepository.findAll();
    }
}
