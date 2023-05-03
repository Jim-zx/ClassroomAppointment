package com.example.system.service;

import com.example.system.domain.Module;

import java.util.ArrayList;

public interface ModuleService {
    public ArrayList<Module> getModule(String auth);

    public ArrayList<Module> getAllModule();
}
