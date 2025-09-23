package com.github.idonneedname.jmcomfessionwall_backend.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AmendPasswordRequest {
    public int user_id;
    public String newpassword;
}
