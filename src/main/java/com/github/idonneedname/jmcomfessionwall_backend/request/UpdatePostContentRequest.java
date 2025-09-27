package com.github.idonneedname.jmcomfessionwall_backend.request;


import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Setter
@Getter
public class UpdatePostContentRequest {
    private int user_id;
    private int post_id;
    private String newcontent;
    public MultipartFile[] pictures;
}
