package com.example.system.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.system.domain.User;
import com.example.system.service.UserService;
import com.example.system.utils.TokenUtil;
import com.example.system.utils.result.DataResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
public class userController {
    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public DataResult<String> login(@RequestBody JSONObject data) {
        DataResult<String> result = new DataResult<>();
        String account = data.getString("account");
        String password = data.getString("password");
        String token = userService.Login(account, password);
        if (token.length() == 0) {
            return result.error(-3, "fail");
        } else {
            result.setDatas(token);
            return result.success();
        }
    }

    @GetMapping("/logout")
    public DataResult<String> logout(@RequestHeader("Authorization") String token) {
        if (userService.Logout(token)) {
            return DataResult.succ();
        } else {
            return DataResult.err();
        }
    }

    @GetMapping("/user")
    public DataResult<User> userInfo(@RequestHeader("Authorization") String token) {
        return DataResult.successByDatas(userService.userInfo(token));
    }

    @GetMapping("/check")
    public DataResult<Boolean> check(@RequestHeader("Authorization") String token) {
        return DataResult.successByDatas(TokenUtil.verify(token));
    }

    @GetMapping("/getAllUser")
    public DataResult<ArrayList<User>> allUser() {
        return DataResult.successByDatas(userService.allUser());
    }

    @PostMapping("/addUser")
    public DataResult<Boolean> addUser(@RequestBody ArrayList<User> users) {
        return DataResult.successByDatas(userService.addUser(users));
    }

    @PostMapping("/deleteUser")
    public DataResult<Boolean> deleteUser(@RequestBody ArrayList<User> users, @RequestHeader("Authorization") String token) {
        String updater = TokenUtil.getUser(token).getAuthorityId();
        if (updater.equals("host") || updater.equals("manage")) {
            return DataResult.successByDatas(userService.deleteUser(users));
        } else {
            return DataResult.err();
        }
    }

    @PostMapping("/updateUser")
    public DataResult<Boolean> updateUser(@RequestBody ArrayList<User> users, @RequestHeader("Authorization") String token) {
        User updater = TokenUtil.getUser(token);
        if (updater.getAuthorityId().equals("host") || updater.getAuthorityId().equals("manage")) {
            return DataResult.successByDatas(userService.updateUser(users, updater));
        } else {
            return DataResult.err();
        }
    }
}
