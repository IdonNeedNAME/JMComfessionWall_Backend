package com.github.idonneedname.jmcomfessionwall_backend.request;

import org.springframework.web.multipart.MultipartFile;

public class UploadPortraitRequest {
    public int user_id;
    public MultipartFile picture;
}
