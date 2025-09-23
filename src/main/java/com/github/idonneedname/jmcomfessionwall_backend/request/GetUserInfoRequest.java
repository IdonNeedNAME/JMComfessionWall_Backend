package com.github.idonneedname.jmcomfessionwall_backend.request;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.RequestBody;

@Getter
@Setter
public class GetUserInfoRequest {
    public int user_id;
    public int target_id;
}
