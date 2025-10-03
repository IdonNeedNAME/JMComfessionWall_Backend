package com.github.idonneedname.jmcomfessionwall_backend.service;


import com.github.idonneedname.jmcomfessionwall_backend.entity.Post;
import com.github.idonneedname.jmcomfessionwall_backend.request.post.GetPostInfoRequest;
import com.github.idonneedname.jmcomfessionwall_backend.request.post.UpdatePostRequest;
import com.github.idonneedname.jmcomfessionwall_backend.request.post.UploadPostRequest;
import com.github.idonneedname.jmcomfessionwall_backend.result.AjaxResult;

import java.util.List;

public interface PostService {
    AjaxResult<String> uploadPost(UploadPostRequest req, String apiKey);
    AjaxResult<List<Post>> getPostOfUser(int target_id, String apiKey);
    AjaxResult<Post> getPostInfo(GetPostInfoRequest req, String apiKey);
    void updatePost(UpdatePostRequest req, String apiKey);
    void postLike(int post_id, String apiKey);
    void postDelete(int post_id, String apiKey);
    AjaxResult<List<Post>> getRecommended(int userId);
}
