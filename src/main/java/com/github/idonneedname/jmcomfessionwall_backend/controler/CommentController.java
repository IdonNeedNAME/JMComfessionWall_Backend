package com.github.idonneedname.jmcomfessionwall_backend.controler;

import com.github.idonneedname.jmcomfessionwall_backend.entity.Comment;
import com.github.idonneedname.jmcomfessionwall_backend.request.GetCommentInfoRequest;
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
    @GetMapping("")
    public AjaxResult<Comment> getCommentInfo(@RequestBody GetCommentInfoRequest req,@RequestHeader("X-API-KEY") String apiKey) {
        log.info(String.valueOf(req.comment_id));
        return commentService.getCommentInfo(req, apiKey);
    }
}
