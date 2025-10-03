package com.github.idonneedname.jmcomfessionwall_backend.service;

import com.github.idonneedname.jmcomfessionwall_backend.entity.Comment;
import com.github.idonneedname.jmcomfessionwall_backend.request.comment.GetCommentInfoRequest;
import com.github.idonneedname.jmcomfessionwall_backend.request.comment.GetCommentsOfPostRequest;
import com.github.idonneedname.jmcomfessionwall_backend.request.comment.UploadCommentRequest;
import com.github.idonneedname.jmcomfessionwall_backend.result.AjaxResult;

import java.util.ArrayList;

public interface CommentService {
    public AjaxResult<String> uploadComment(UploadCommentRequest req,String apiKey);
    public AjaxResult<ArrayList<Comment>> getCommentsOfPost(GetCommentsOfPostRequest req, String apiKey);
    public AjaxResult<Comment> getCommentInfo(GetCommentInfoRequest req, String apiKey);
    public AjaxResult<String> commentLike(int userId, int commentId);
}
