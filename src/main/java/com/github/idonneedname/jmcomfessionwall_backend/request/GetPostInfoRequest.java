package com.github.idonneedname.jmcomfessionwall_backend.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetPostInfoRequest {
    public int user_id;
    public int post_id;
}
