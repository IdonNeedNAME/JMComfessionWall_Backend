package com.github.idonneedname.jmcomfessionwall_backend.controler;

import com.github.idonneedname.jmcomfessionwall_backend.request.UploadCommentRequest;
import com.github.idonneedname.jmcomfessionwall_backend.request.UploadPostRequest;
import com.github.idonneedname.jmcomfessionwall_backend.result.AjaxResult;
import com.github.idonneedname.jmcomfessionwall_backend.service.CommentService;
import com.github.idonneedname.jmcomfessionwall_backend.service.PostService;
import com.github.idonneedname.jmcomfessionwall_backend.service.impl.CommentServiceImpl;
import com.github.idonneedname.jmcomfessionwall_backend.service.impl.PostServiceImpl;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/post")
@Slf4j
public class PostControler {
    @Resource
    private PostServiceImpl postService;
    @Resource
    private CommentServiceImpl commentService;
    @PostMapping(
            value = "/upload",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public AjaxResult<String> uploadPost(UploadPostRequest req, @RequestHeader("X-API-KEY") String api) {
        return postService.uploadPost(req,api);
    }
    @PostMapping("/comment")
    public AjaxResult<String> uploadComment(@RequestBody UploadCommentRequest req, @RequestHeader("X-API-KEY") String api)
    {
        return commentService.uploadComment(req,api);
    }
}
