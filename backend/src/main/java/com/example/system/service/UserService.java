package com.example.system.service;


import com.example.system.domain.User;

import java.util.ArrayList;

public interface UserService {
    /**
    * Login
    * **/
    String Login(String email,String password);

    boolean Logout(String token);

    User userInfo(String token);

    ArrayList<User> allUser();

    boolean addUser(ArrayList<User> users);

    boolean deleteUser(ArrayList<User> users);

    boolean updateUser(ArrayList<User> users,User user);

}
