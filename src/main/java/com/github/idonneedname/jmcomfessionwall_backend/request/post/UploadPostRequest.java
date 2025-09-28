package com.github.idonneedname.jmcomfessionwall_backend.request.post;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class UploadPostRequest {
    public int user_id;
    public String title;
    public String content;
    public MultipartFile[] pictures;
    public boolean ispublic;
    public boolean anonymity;
}
