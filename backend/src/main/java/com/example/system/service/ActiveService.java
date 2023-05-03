package com.example.system.service;

import com.example.system.domain.Active;

import java.util.ArrayList;

public interface ActiveService {
    ArrayList<Active> allActive();

    boolean addActive(ArrayList<Active> actives);
}
