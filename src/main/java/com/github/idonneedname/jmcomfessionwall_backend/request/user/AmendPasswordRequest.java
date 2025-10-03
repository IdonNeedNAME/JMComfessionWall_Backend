package com.github.idonneedname.jmcomfessionwall_backend.request.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AmendPasswordRequest {
    private String originpassword;
    private String newpassword;
}
