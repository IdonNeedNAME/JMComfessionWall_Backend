package com.github.idonneedname.jmcomfessionwall_backend.service;


import com.github.idonneedname.jmcomfessionwall_backend.entity.Post;
import com.github.idonneedname.jmcomfessionwall_backend.request.UploadPostRequest;
import com.github.idonneedname.jmcomfessionwall_backend.result.AjaxResult;

public interface PostService {
    public AjaxResult<String> uploadPost(UploadPostRequest req,String apiKey);
}
