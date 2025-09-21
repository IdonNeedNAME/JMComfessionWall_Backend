package com.github.idonneedname.jmcomfessionwall_backend.controler;

import com.github.idonneedname.jmcomfessionwall_backend.mapper.UserMapper;
import com.github.idonneedname.jmcomfessionwall_backend.request.LoginRequest;
import com.github.idonneedname.jmcomfessionwall_backend.request.RegisterRequest;
import com.github.idonneedname.jmcomfessionwall_backend.result.AjaxResult;
import com.github.idonneedname.jmcomfessionwall_backend.service.UserService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
@Slf4j
public class UserControler {
    @Resource
    private UserService userService;

    @PostMapping("/register")
    public AjaxResult<String> register(@RequestBody RegisterRequest req){
        return userService.register(req);
    }
    @PostMapping("/login")
    public AjaxResult<com.github.idonneedname.jmcomfessionwall_backend.entity.User> login(@RequestBody LoginRequest req){
        return userService.login(req);
    }
}
