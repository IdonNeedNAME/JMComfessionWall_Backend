package com.github.idonneedname.jmcomfessionwall_backend.service;

import com.github.idonneedname.jmcomfessionwall_backend.entity.Comment;
import com.github.idonneedname.jmcomfessionwall_backend.request.GetCommentInfoRequest;
import com.github.idonneedname.jmcomfessionwall_backend.request.GetCommentsOfPostRequest;
import com.github.idonneedname.jmcomfessionwall_backend.request.UploadCommentRequest;
import com.github.idonneedname.jmcomfessionwall_backend.result.AjaxResult;

import java.util.ArrayList;

public interface CommentService {
    public AjaxResult<String> uploadComment(UploadCommentRequest req,String apiKey);
    public AjaxResult<ArrayList<Comment>> getCommentsOfPost(GetCommentsOfPostRequest req, String apiKey);
    public AjaxResult<Comment> getCommentInfo(GetCommentInfoRequest req, String apiKey);
}
