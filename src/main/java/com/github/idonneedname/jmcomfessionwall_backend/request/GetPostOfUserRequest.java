package com.github.idonneedname.jmcomfessionwall_backend.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetPostOfUserRequest {
    public int user_id;
    public int target_id;
}
