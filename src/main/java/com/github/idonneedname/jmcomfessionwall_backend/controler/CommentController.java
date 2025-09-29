package com.github.idonneedname.jmcomfessionwall_backend.controler;

import com.github.idonneedname.jmcomfessionwall_backend.entity.Comment;
import com.github.idonneedname.jmcomfessionwall_backend.helper.ApiKeyHelper;
import com.github.idonneedname.jmcomfessionwall_backend.request.comment.GetCommentInfoRequest;
import com.github.idonneedname.jmcomfessionwall_backend.request.comment.UploadCommentRequest;
import com.github.idonneedname.jmcomfessionwall_backend.result.AjaxResult;
import com.github.idonneedname.jmcomfessionwall_backend.service.CommentService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/comment")
@Slf4j
public class CommentController {
    @Resource
    private CommentService commentService;
    @Resource
    private ApiKeyHelper apiKeyHelper;
    @GetMapping("/{commentId}")
    public AjaxResult<Comment> getCommentInfo(@PathVariable int commentId,@RequestHeader("X-API-KEY") String apiKey) {
        GetCommentInfoRequest req =  new GetCommentInfoRequest();
        req.comment_id = commentId;
        req.user_id=apiKeyHelper.getUserId(apiKey);
        return commentService.getCommentInfo(req, apiKey);
    }
    @PostMapping("/comment/{commentId}")
    public AjaxResult<String> uploadCommentToComment(@RequestParam String content,@PathVariable int commentId, @RequestHeader("X-API-KEY") String api)
    {
        UploadCommentRequest req =  new UploadCommentRequest();
        req.content=content;
        req.type=0;
        req.user_id=apiKeyHelper.getUserId(api);
        req.target_id=commentId;
        return commentService.uploadComment(req,api);
    }
    @PostMapping("/post/{postId}")
    public AjaxResult<String> uploadCommentToPost(@RequestParam String content,@PathVariable int postId, @RequestHeader("X-API-KEY") String api)
    {
        UploadCommentRequest req =  new UploadCommentRequest();
        req.content=content;
        req.type=1;
        req.user_id=apiKeyHelper.getUserId(api);
        req.target_id=postId;
        return commentService.uploadComment(req,api);
    }
}
