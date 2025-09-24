package com.github.idonneedname.jmcomfessionwall_backend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.idonneedname.jmcomfessionwall_backend.entity.Comment;
import com.github.idonneedname.jmcomfessionwall_backend.entity.Post;
import com.github.idonneedname.jmcomfessionwall_backend.exception.ApiException;
import com.github.idonneedname.jmcomfessionwall_backend.helper.ApiKeyHelper;
import com.github.idonneedname.jmcomfessionwall_backend.helper.ArrayNodeHelper;
import com.github.idonneedname.jmcomfessionwall_backend.mapper.CommentMapper;
import com.github.idonneedname.jmcomfessionwall_backend.mapper.PostMapper;
import com.github.idonneedname.jmcomfessionwall_backend.request.UploadCommentRequest;
import com.github.idonneedname.jmcomfessionwall_backend.result.AjaxResult;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

import static com.github.idonneedname.jmcomfessionwall_backend.constant.ExceptionEnum.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class CommentServiceImpl {
    @Resource
    private PostMapper postMapper;
    @Resource
    private CommentMapper commentMapper;
    public AjaxResult<String> uploadComment(UploadCommentRequest req,String apiKey)
    {
        if(!ApiKeyHelper.isVaildApiKey(req.user_id,apiKey))
            throw new ApiException(INVALID_APIKEY);

        Comment comment = new Comment();
        comment.setContent(req.content);
        comment.host=req.user_id;
        comment.likelist="[]";
        comment.subcomment="[]";
        comment.hidden=false;
        commentMapper.insert(comment);
        if(req.type==1)
        {
            QueryWrapper<Post> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("id",req.target_id);
            Post post = postMapper.selectOne(queryWrapper);
            if(post!=null)
            {
                post.subcomment= ArrayNodeHelper.add(post.subcomment,comment.id);
                comment.depth=post.depth++;
                postMapper.update(post,queryWrapper);
            }
            else
                throw new ApiException(POST_NOT_FOUND);
        }
        else
        {
            QueryWrapper<Comment> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("id",req.target_id);
            Comment dad = commentMapper.selectOne(queryWrapper);
            if(dad!=null)
            {
                dad.subcomment= ArrayNodeHelper.add(dad.subcomment,comment.id);
                comment.depth=dad.depth++;
                commentMapper.update(comment,queryWrapper);
            }
            else
                throw new ApiException(COMMENT_NOT_FOUND);
        }
        commentMapper.updateById(comment);
        return AjaxResult.success();
    }
}
