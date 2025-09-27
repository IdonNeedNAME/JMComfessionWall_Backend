package com.github.idonneedname.jmcomfessionwall_backend.request;


import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class amendAnonymityRequest {
    private int user_id;
    private boolean anonymity;
    private int post_id;
}
