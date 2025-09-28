package com.github.idonneedname.jmcomfessionwall_backend.request.RegAndLog;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterRequest {
    public String username;
    public int type;
    public String name;
    public String password;
}
