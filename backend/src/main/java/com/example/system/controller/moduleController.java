package com.example.system.controller;

import com.example.system.domain.Module;
import com.example.system.domain.User;
import com.example.system.service.ModuleService;
import com.example.system.utils.TokenUtil;
import com.example.system.utils.result.DataResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
public class moduleController {
    @Autowired
    private ModuleService moduleService;

    @GetMapping("/getModule")
    public DataResult<ArrayList<Module>> getModule(@RequestHeader("Authorization") String token){
        User user=TokenUtil.getUser(token);
        return DataResult.successByDatas(moduleService.getModule(user.getAuthorityId()));
    }

    @GetMapping("/getAllModule")
    public DataResult<ArrayList<Module>> getAllModule(){
        return DataResult.successByDatas(moduleService.getAllModule());
    }
}
