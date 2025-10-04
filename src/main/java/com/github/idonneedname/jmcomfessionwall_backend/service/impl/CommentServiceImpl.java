package com.github.idonneedname.jmcomfessionwall_backend.service.impl;

import com.github.idonneedname.jmcomfessionwall_backend.constant.Constant;
import com.github.idonneedname.jmcomfessionwall_backend.entity.Comment;
import com.github.idonneedname.jmcomfessionwall_backend.entity.Post;
import com.github.idonneedname.jmcomfessionwall_backend.entity.User;
import com.github.idonneedname.jmcomfessionwall_backend.exception.ApiException;
import com.github.idonneedname.jmcomfessionwall_backend.helper.ApiKeyHelper;
import com.github.idonneedname.jmcomfessionwall_backend.helper.ArrayNodeHelper;
import com.github.idonneedname.jmcomfessionwall_backend.helper.AssembleHelper;
import com.github.idonneedname.jmcomfessionwall_backend.helper.event.CommentLikeEvent;
import com.github.idonneedname.jmcomfessionwall_backend.mapper.CommentMapper;
import com.github.idonneedname.jmcomfessionwall_backend.mapper.PostMapper;
import com.github.idonneedname.jmcomfessionwall_backend.mapper.UserMapper;
import com.github.idonneedname.jmcomfessionwall_backend.request.comment.GetCommentInfoRequest;
import com.github.idonneedname.jmcomfessionwall_backend.request.comment.GetCommentsOfPostRequest;
import com.github.idonneedname.jmcomfessionwall_backend.request.comment.UploadCommentRequest;
import com.github.idonneedname.jmcomfessionwall_backend.result.AjaxResult;
import com.github.idonneedname.jmcomfessionwall_backend.service.CommentService;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

import static com.github.idonneedname.jmcomfessionwall_backend.constant.ExceptionEnum.*;
import static com.github.idonneedname.jmcomfessionwall_backend.constant.ExceptionEnum.HOST_ADD_LIKE;

@Service
@RequiredArgsConstructor
@Slf4j
public class CommentServiceImpl implements CommentService {
    @Resource
    private PostMapper postMapper;
    @Resource
    private CommentMapper commentMapper;
    @Resource
    private AssembleHelper assembleHelper;
    @Resource
    private ApiKeyHelper apiKeyHelper;
    @Resource
    private UserMapper userMapper;
    @Override
    public AjaxResult<String> uploadComment(UploadCommentRequest req,String apiKey)
    {
        Comment comment = new Comment();
        comment.setContent(req.content);
        comment.host=req.user_id;
        comment.likelist="[]";
        comment.subcomment="[]";
        comment.hidden=false;
        comment.dadtype=req.type;
        comment.dadid=req.target_id;
        commentMapper.insert(comment);
        if(req.type==1)
        {
            Post post= Constant.pushCache.getPost(req.target_id);
            if(post!=null)
            {
                post.subcomment= ArrayNodeHelper.add(post.subcomment,comment.id);
                post.comments++;
                comment.depth=post.depth+1;
                Constant.postCache.tryUpdate(post);
            }
            else
                throw new ApiException(POST_NOT_FOUND);
        }
        else
        {
            Comment dad = commentMapper.selectById(req.target_id);
            if(dad!=null)
            {
                dad.subcomment= ArrayNodeHelper.add(dad.subcomment,comment.id);
                comment.depth=dad.depth+1;
                comment.comments++;
                Post post= Constant.pushCache.getPost(dad.dadid);
                post.comments++;
                Constant.postCache.tryUpdate(post);
                commentMapper.updateById(comment);
            }
            else
                throw new ApiException(COMMENT_NOT_FOUND);
        }
        commentMapper.updateById(comment);
        return AjaxResult.success();
    }
    @Override
    public AjaxResult<ArrayList<Comment>> getCommentsOfPost(GetCommentsOfPostRequest req, String apiKey)
    {
        Post post = postMapper.selectById(req.post_id);
        if(post==null)
            throw new ApiException(POST_NOT_FOUND);
        assembleHelper.assemble(post,req.user_id,true);
        return AjaxResult.success(post.subcomments);
    }
    @Override
    public AjaxResult<Comment> getCommentInfo(GetCommentInfoRequest req, String apiKey)
    {
        Comment comment = commentMapper.selectById(req.comment_id);
        if(comment==null)
            throw new ApiException(COMMENT_NOT_FOUND);
        assembleHelper.assemble(comment,req.user_id);
        return AjaxResult.success(comment);
    }
    @Override
    public AjaxResult<String> commentLike(int userId,int commentId)
    {
        Comment comment = commentMapper.selectById(commentId);
        if (comment==null)
            throw new ApiException(COMMENT_NOT_FOUND);
        if(userId==comment.host)
            throw new ApiException(HOST_ADD_LIKE);
        CommentLikeEvent event=new CommentLikeEvent(Constant.eventHandler,userId,comment,commentMapper);
        Constant.eventHandler.addEvent(event);
        return AjaxResult.success();
    }

    @Override
    public void commentDelete(int commentId, String apiKey) {
        int userId = apiKeyHelper.getUserId(apiKey);
        Comment comment = commentMapper.selectById(commentId);
        if (comment==null)
            throw new ApiException(COMMENT_NOT_FOUND);
        if(userId!=comment.host)
            throw new ApiException(PERMISSION_NOT_ALLOWED);
        commentMapper.deleteById(commentId);
    }
}
