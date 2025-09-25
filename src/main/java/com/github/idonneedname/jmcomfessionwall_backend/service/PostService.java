package com.github.idonneedname.jmcomfessionwall_backend.service;


import com.github.idonneedname.jmcomfessionwall_backend.entity.Post;
import com.github.idonneedname.jmcomfessionwall_backend.request.GetCommentsOfPostRequest;
import com.github.idonneedname.jmcomfessionwall_backend.request.GetPostInfoRequest;
import com.github.idonneedname.jmcomfessionwall_backend.request.GetPostOfUserRequest;
import com.github.idonneedname.jmcomfessionwall_backend.request.UploadPostRequest;
import com.github.idonneedname.jmcomfessionwall_backend.result.AjaxResult;

import java.util.ArrayList;
import java.util.List;

public interface PostService {
    public AjaxResult<String> uploadPost(UploadPostRequest req,String apiKey);
    public AjaxResult<List<Post>> getPostOfUser(GetPostOfUserRequest req, String apiKey);
    public AjaxResult<Post> getPostInfo(GetPostInfoRequest req, String apiKey);
}
