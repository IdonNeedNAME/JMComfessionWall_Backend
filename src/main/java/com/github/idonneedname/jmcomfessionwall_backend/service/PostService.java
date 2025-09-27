package com.github.idonneedname.jmcomfessionwall_backend.service;


import com.github.idonneedname.jmcomfessionwall_backend.entity.Post;
import com.github.idonneedname.jmcomfessionwall_backend.request.*;
import com.github.idonneedname.jmcomfessionwall_backend.result.AjaxResult;

import java.util.List;

public interface PostService {
    public AjaxResult<String> uploadPost(UploadPostRequest req,String apiKey);
    public AjaxResult<List<Post>> getPostOfUser(GetPostOfUserRequest req, String apiKey);
    public AjaxResult<Post> getPostInfo(GetPostInfoRequest req, String apiKey);
    public void updatePostContent(UpdatePostContentRequest req, String apiKey);
    public void updatePostTitle(UpdatePostTitleRequest req, String apiKey);
    public void updatePost(UpdatePostRequest req, String apiKey);
}
