package com.example.system.service.impl;

import com.example.system.domain.User;
import com.example.system.repository.UserRepository;
import com.example.system.service.UserService;
import com.example.system.utils.MD5Util;
import com.example.system.utils.TokenUtil;
import com.example.system.utils.UpdateUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Resource
    private UserRepository userRepository;

    @Override
    public String Login(String email, String password) {
        //加密
        password = MD5Util.string2MD5(password);
        User user = userRepository.findByEmailAndPassword(email, password);
        if (user != null) {
            return TokenUtil.generateToken(user);
        } else {
            return "";
        }
    }

    @Override
    public boolean Logout(String token) {
        return TokenUtil.logout(token);
    }

    @Override
    public User userInfo(String token) {
        return TokenUtil.getUser(token);
    }

    @Override
    public ArrayList<User> allUser() {
        return (ArrayList<User>) userRepository.findAll();
    }

    @Override
    public boolean addUser(ArrayList<User> users) {
        for (User user : users) {
            User repeat = userRepository.findByEmail(user.getEmail());
            if (repeat == null) {
                user.setPassword(MD5Util.string2MD5(user.getPassword()));
            } else {
                return false;
            }
        }
        userRepository.saveAll(users);
        return true;
    }

    @Override
    public boolean deleteUser(ArrayList<User> users) {
        for (User user : users) {
            if (user.getId() == 1) {
                return false;
            }
            Optional<User> repeat = userRepository.findById(user.getId());
            if (!repeat.isPresent()) {
                return false;
            }
        }
        userRepository.deleteAll(users);
        return true;
    }

    @Override
    public boolean updateUser(ArrayList<User> users, User user) {
        for (User user1 : users) {
            UpdateUtil.copyNotNullProperties(userRepository,user);
            if (user.getAuthorityId().equals("manage") && user1.getId() == 1) {
                return false;
            }
            if (user1.getId()==1 && !user1.getAuthorityId().equals("host")){
                return false;
            }
            if (userRepository.findByEmail(user1.getEmail())!=null) {
                return false;
            }
        }
        userRepository.saveAll(users);
        return true;
    }


}
