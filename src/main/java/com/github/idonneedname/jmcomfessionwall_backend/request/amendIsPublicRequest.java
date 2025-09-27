package com.github.idonneedname.jmcomfessionwall_backend.request;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class amendIsPublicRequest {
    private int user_id;
    private boolean isPublic;
    private int post_id;
}
