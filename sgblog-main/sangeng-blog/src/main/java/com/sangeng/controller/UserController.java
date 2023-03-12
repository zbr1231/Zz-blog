package com.sangeng.controller;

import com.sangeng.annotation.SystemLog;
import com.sangeng.domain.ResponseResult;
import com.sangeng.domain.entity.User;
import com.sangeng.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/userInfoPersonal")
    public ResponseResult userInfoPersonal(){
        return userService.userInfoPersonal();
    }

    @GetMapping("/userInfo")
    public ResponseResult userInfo(Long userId) {
        log.warn("/user/userInfo {}", userId);
        return userService.userInfo(userId);
    }

    @GetMapping("/countInfo")
    public ResponseResult countInfo(Long userId){
        log.warn("/user/countInfo {}", userId);
        return userService.countInfo(userId);
    }

    @PutMapping("/userInfo")
    @SystemLog(businessName = "更新用户信息")
    public ResponseResult updateUserInfo(@RequestBody User user){
        return userService.updateUserInfo(user);
    }

    @PostMapping("/register")
    public ResponseResult register(@RequestBody User user){
        return userService.register(user);
    }
}
