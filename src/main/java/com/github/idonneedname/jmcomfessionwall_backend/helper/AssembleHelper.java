package com.github.idonneedname.jmcomfessionwall_backend.helper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.idonneedname.jmcomfessionwall_backend.constant.Constant;
import com.github.idonneedname.jmcomfessionwall_backend.entity.Comment;
import com.github.idonneedname.jmcomfessionwall_backend.entity.Picture;
import com.github.idonneedname.jmcomfessionwall_backend.entity.Post;
import com.github.idonneedname.jmcomfessionwall_backend.entity.User;
import com.github.idonneedname.jmcomfessionwall_backend.mapper.CommentMapper;
import com.github.idonneedname.jmcomfessionwall_backend.mapper.PictureMapper;
import com.github.idonneedname.jmcomfessionwall_backend.mapper.PostMapper;
import com.github.idonneedname.jmcomfessionwall_backend.mapper.UserMapper;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Slf4j
@Service
public class AssembleHelper {
    @Resource
    PictureHelper pictureHelper;
    @Resource
    PictureMapper  pictureMapper;
    @Resource
    UserMapper userMapper;
    @Resource
    CommentMapper commentMapper;
    @Resource
    PostMapper postMapper;
    public void assemble(Picture picture,int id)//配置图片具体内容
    {
        QueryWrapper<Picture> wrapper = new QueryWrapper<>();
        wrapper.eq("id",id);
        Picture pic;
        pic =pictureMapper.selectOne(wrapper);
        if(pic==null)
        {
            picture.width=Constant.defaultPicture.width;
            picture.height=Constant.defaultPicture.height;
            picture.url=Constant.baseUrl+Constant.defaultPicture.name;
            return ;
        }
        picture.width=pic.getWidth();
        picture.height=pic.getHeight();
        picture.url= Constant.baseUrl+pic.name;
    }
    public void assemble(User user)//配置用户信息具体内容
    {
        if(user.portrait==null)
        {
            user.portrait=new Picture();
            assemble(user.portrait,user.pictureref);
        }
    }
    //配置帖子具体内容
    public void assemble(Post post, int visitor, boolean shouldInitialComments)//填-1表示没有
    {
        //点赞
        ArrayList<Integer> like= ArrayNodeHelper.translateToArray(post.likelist);
        if(like!=null)
        {
            post.likes=like.size();
            post.liked=false;
            if(visitor!=-1)
            {
                if(ArrayNodeHelper.idInArray(post.likelist,visitor)!=-1)
                    post.liked=true;
            }
        }
        //图片
        ArrayList<Integer> pics= ArrayNodeHelper.translateToArray(post.picture);
        if(pics!=null&&!pics.isEmpty())
        {
            post.pictures=new ArrayList<>();
            for(int i=0;i<pics.size();i++)
            {
                Picture picture=new Picture();
                assemble(picture,pics.get(i));
                post.pictures.add(picture);
            }
        }
        //子评论
            ArrayList<Integer> comments= ArrayNodeHelper.translateToArray(post.subcomment);
            if(comments!=null&&!comments.isEmpty())
            {

                post.comments =0;
                if(post.depth<3)
                {
                    post.subcomments=new ArrayList<>();
                    int sub;
                    for(int i=0;i<comments.size();i++)
                    {
                        sub=comments.get(i);
                        StringHelper.log(sub);
                        QueryWrapper<Comment> wrapper=new QueryWrapper<>();
                        wrapper.eq("id",sub);
                        Comment subcomment=commentMapper.selectById(sub);
                        if(subcomment.hidden)
                            continue;
                        if(shouldInitialComments)
                        {
                            assemble(subcomment,visitor);
                            post.subcomments.add(subcomment);
                        }
                        post.comments++;
                    }
                }

            }
    }
    //配置评论信息具体内容
    public void assemble(Comment comment,int target)
    {
        //点赞
        ArrayList<Integer> like= ArrayNodeHelper.translateToArray(comment.likelist);
        if(like!=null)
        {
            comment.likes=like.size();
            comment.liked=false;
            if(target!=-1)
            {
                if(ArrayNodeHelper.idInArray(comment.likelist,target)!=-1)
                    comment.liked=true;
            }
        }
        //子评论
        ArrayList<Integer> comments= ArrayNodeHelper.translateToArray(comment.subcomment);
        if(comments!=null)
        {
            comment.subcomments=new ArrayList<>();
            comment.comments =comments.size();
            if(comment.depth<=2)
            {
                int sub;
                for(int i=0;i<comments.size();i++)
                {
                    sub=comments.get(i);
                    QueryWrapper<Comment> wrapper=new QueryWrapper<>();
                    wrapper.eq("id",sub);
                    Comment subcomment=commentMapper.selectById(sub);
                    assemble(subcomment,target);
                    comment.subcomments.add(subcomment);
                }
            }

        }
    }
}
