package com.github.idonneedname.jmcomfessionwall_backend.request.post;


import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Setter
@Getter
public class UpdatePostRequest {
    private int user_id;
    private int post_id;
    private String newContent;
    public MultipartFile[] pictures;
    private String newTitle;
    private boolean anonymity;
    private boolean isPublic;
}
