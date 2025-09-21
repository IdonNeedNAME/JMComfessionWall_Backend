package com.github.idonneedname.jmcomfessionwall_backend.service;

import com.github.idonneedname.jmcomfessionwall_backend.entity.User;
import com.github.idonneedname.jmcomfessionwall_backend.request.LoginRequest;
import com.github.idonneedname.jmcomfessionwall_backend.request.RegisterRequest;
import com.github.idonneedname.jmcomfessionwall_backend.result.AjaxResult;

public interface UserService {
    public AjaxResult<User> login(LoginRequest req);
    public AjaxResult<String> register(RegisterRequest req);
}
