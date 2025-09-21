package com.github.idonneedname.jmcomfessionwall_backend.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {
    public String username;
    public String password;
}
