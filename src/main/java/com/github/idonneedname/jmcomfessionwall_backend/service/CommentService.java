package com.github.idonneedname.jmcomfessionwall_backend.service;

import com.github.idonneedname.jmcomfessionwall_backend.request.UploadCommentRequest;
import com.github.idonneedname.jmcomfessionwall_backend.result.AjaxResult;

public interface CommentService {
    public AjaxResult<String> uploadComment(UploadCommentRequest req,String apiKey);
}
