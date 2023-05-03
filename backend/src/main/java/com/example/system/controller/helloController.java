package com.example.system.controller;

import com.example.system.domain.User;
import com.example.system.domain.newTable;
import com.example.system.repository.UserRepository;
import com.example.system.repository.newRe;
import com.example.system.utils.result.DataResult;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

@RestController
public class helloController {
    @Resource
    UserRepository userRepository;

    @GetMapping("/hello")
    public Optional<User> hello(){
        return userRepository.findById((long)1);
    }


}
