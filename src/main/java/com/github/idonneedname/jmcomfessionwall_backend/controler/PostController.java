package com.github.idonneedname.jmcomfessionwall_backend.controler;

import com.github.idonneedname.jmcomfessionwall_backend.entity.Comment;
import com.github.idonneedname.jmcomfessionwall_backend.entity.Post;
import com.github.idonneedname.jmcomfessionwall_backend.helper.StringHelper;
import com.github.idonneedname.jmcomfessionwall_backend.request.*;
import com.github.idonneedname.jmcomfessionwall_backend.result.AjaxResult;
import com.github.idonneedname.jmcomfessionwall_backend.service.impl.CommentServiceImpl;
import com.github.idonneedname.jmcomfessionwall_backend.service.impl.PostServiceImpl;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/post")
@Slf4j
public class PostController {
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
    @GetMapping
    public AjaxResult<Post> getPostInfo(@RequestBody GetPostInfoRequest req, @RequestHeader("X-API-KEY") String api) {
        return postService.getPostInfo(req,api);
    }
    @GetMapping("/comment")
    public AjaxResult<ArrayList<Comment>> getCommentsOfPost(@RequestBody GetCommentsOfPostRequest req, @RequestHeader("X-API-KEY") String api)
    {
        return commentService.getCommentsOfPost(req,api);
    }
    @PatchMapping("/amend/content")
    public AjaxResult<Void> updatePostContest(UpdatePostContentRequest req, @RequestHeader("X-API-KEY") String api) {
        postService.updatePostContent(req,api);
        return AjaxResult.success();
    }
    @PatchMapping("/amend/title")
    public AjaxResult<Void> updatePostTitle(@RequestBody UpdatePostTitleRequest req, @RequestHeader("X-API-KEY") String api){
        postService.updatePostTitle(req,api);
        return AjaxResult.success();
    }
    @PatchMapping("/amend")
    public AjaxResult<Void> updatePost(UpdatePostRequest req, @RequestHeader("X-API-KEY") String api){
        postService.updatePost(req,api);
        return AjaxResult.success();
    }
    @PatchMapping("/amend/anonymity")
    public AjaxResult<Void> amendAnonymity(@RequestBody amendAnonymityRequest req, @RequestHeader("X-API-KEY") String api){
        postService.amendAnonymity(req,api);
        return AjaxResult.success();
    }
    @PatchMapping("/amend/public")
    public AjaxResult<Void> amendIsPublic(@RequestBody amendIsPublicRequest req, @RequestHeader("X-API-KEY") String api){
        postService.amendIsPublic(req,api);
        return AjaxResult.success();
    }
}
