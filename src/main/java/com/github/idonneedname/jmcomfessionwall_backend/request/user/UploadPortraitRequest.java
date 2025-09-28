package com.github.idonneedname.jmcomfessionwall_backend.request.user;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class UploadPortraitRequest {
    public int user_id;
    public MultipartFile picture;
}
