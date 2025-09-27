package com.github.idonneedname.jmcomfessionwall_backend.request;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UpdatePostTitleRequest {
    private int user_id;
    private int post_id;
    private String newtitle;
}
