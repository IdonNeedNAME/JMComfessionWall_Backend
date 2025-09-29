package com.github.idonneedname.jmcomfessionwall_backend.Response;


import com.github.idonneedname.jmcomfessionwall_backend.entity.Picture;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserInfoResponse {
    private int id;
    private String username;
    private String name;
    private Picture portrait;
    private int type;
}
