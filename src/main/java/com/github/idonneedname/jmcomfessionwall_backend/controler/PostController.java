package com.github.idonneedname.jmcomfessionwall_backend.controler;

import com.github.idonneedname.jmcomfessionwall_backend.entity.Comment;
import com.github.idonneedname.jmcomfessionwall_backend.entity.Post;
import com.github.idonneedname.jmcomfessionwall_backend.helper.ApiKeyHelper;
import com.github.idonneedname.jmcomfessionwall_backend.mapper.PostMapper;
import com.github.idonneedname.jmcomfessionwall_backend.request.GetUnCheckedReportsRequest;
import com.github.idonneedname.jmcomfessionwall_backend.request.comment.GetCommentsOfPostRequest;
import com.github.idonneedname.jmcomfessionwall_backend.request.comment.UploadCommentRequest;
import com.github.idonneedname.jmcomfessionwall_backend.request.post.GetPostInfoRequest;
import com.github.idonneedname.jmcomfessionwall_backend.request.post.UpdatePostRequest;
import com.github.idonneedname.jmcomfessionwall_backend.request.post.UploadPostRequest;
import com.github.idonneedname.jmcomfessionwall_backend.result.AjaxResult;
import com.github.idonneedname.jmcomfessionwall_backend.service.impl.CommentServiceImpl;
import com.github.idonneedname.jmcomfessionwall_backend.service.impl.PostServiceImpl;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    private PostMapper postMapper;
    @Autowired
    private ApiKeyHelper apiKeyHelper;

    @PostMapping(
            value = "",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public AjaxResult<String> uploadPost(UploadPostRequest req, @RequestHeader("X-API-KEY") String api) {
        req.user_id=apiKeyHelper.getUserId(api);
        return postService.uploadPost(req,api);
    }

    @GetMapping("/{postId}")
    public AjaxResult<Post> getPostInfo(@PathVariable int postId, @RequestHeader("X-API-KEY") String api) {
        GetPostInfoRequest req=new GetPostInfoRequest();
        req.post_id = postId;
        req.user_id = apiKeyHelper.getUserId(api);
        return postService.getPostInfo(req,api);
    }
    @GetMapping("/{postId}/comments")
    public AjaxResult<ArrayList<Comment>> getCommentsOfPost(@PathVariable int postId, @RequestHeader("X-API-KEY") String api)
    {
        GetCommentsOfPostRequest req=new GetCommentsOfPostRequest();
        req.post_id=postId;
        req.user_id=apiKeyHelper.getUserId(api);
        return commentService.getCommentsOfPost(req,api);
    }
    @PatchMapping("/{postId}")
    public AjaxResult<Void> updatePost(@PathVariable("postId") int id, UpdatePostRequest req, @RequestHeader("X-API-KEY") String api){
        req.setPost_id(id);
        postService.updatePost(req,api);
        return AjaxResult.success();
    }
    @PostMapping("/{postId}/like")
    public AjaxResult<Void> postLike(@PathVariable("postId") int id,@RequestHeader("X-API-KEY") String api){
        postService.postLike(id,api);
        return AjaxResult.success();
    }
    @DeleteMapping("/{postId}")
    public AjaxResult<Void> postDelete(@PathVariable("postId") int id,@RequestHeader("X-API-KEY") String api){
        postService.postDelete(id,api);
        return AjaxResult.success();
    }
}
