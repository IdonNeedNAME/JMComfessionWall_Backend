package com.github.idonneedname.jmcomfessionwall_backend.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AmendNameRequest {
    public int user_id;
    public String newname;
}
