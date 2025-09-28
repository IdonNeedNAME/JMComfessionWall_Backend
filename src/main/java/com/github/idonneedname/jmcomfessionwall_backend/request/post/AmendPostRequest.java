package com.github.idonneedname.jmcomfessionwall_backend.request.post;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class AmendPostRequest {
    public int user_id;
    public int post_id;
    public MultipartFile[]  pictures;
    public String newcontent;
    public String newtitle;
}
