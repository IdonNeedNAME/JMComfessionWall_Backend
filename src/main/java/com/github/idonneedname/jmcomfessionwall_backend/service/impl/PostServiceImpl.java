package com.github.idonneedname.jmcomfessionwall_backend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.github.idonneedname.jmcomfessionwall_backend.constant.Constant;
import com.github.idonneedname.jmcomfessionwall_backend.entity.Post;
import com.github.idonneedname.jmcomfessionwall_backend.entity.User;
import com.github.idonneedname.jmcomfessionwall_backend.exception.ApiException;
import com.github.idonneedname.jmcomfessionwall_backend.helper.ApiKeyHelper;
import com.github.idonneedname.jmcomfessionwall_backend.helper.AssembleHelper;
import com.github.idonneedname.jmcomfessionwall_backend.helper.PictureHelper;
import com.github.idonneedname.jmcomfessionwall_backend.helper.StringHelper;
import com.github.idonneedname.jmcomfessionwall_backend.helper.event.LikeEvent;
import com.github.idonneedname.jmcomfessionwall_backend.mapper.PictureMapper;
import com.github.idonneedname.jmcomfessionwall_backend.mapper.PostMapper;
import com.github.idonneedname.jmcomfessionwall_backend.mapper.UserMapper;
import com.github.idonneedname.jmcomfessionwall_backend.request.post.GetPostInfoRequest;
import com.github.idonneedname.jmcomfessionwall_backend.request.post.UpdatePostRequest;
import com.github.idonneedname.jmcomfessionwall_backend.request.post.UploadPostRequest;
import com.github.idonneedname.jmcomfessionwall_backend.result.AjaxResult;
import com.github.idonneedname.jmcomfessionwall_backend.service.PostService;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.Instant;
import java.util.List;

import static com.github.idonneedname.jmcomfessionwall_backend.constant.ExceptionEnum.*;
import static com.github.idonneedname.jmcomfessionwall_backend.helper.ArrayNodeHelper.*;


@Service
@RequiredArgsConstructor
@Slf4j
public class PostServiceImpl implements PostService {
    @Resource
    private PostMapper postMapper;
    @Resource
    private PictureHelper pictureHelper;
    @Resource
    private PictureMapper pictureMapper;
    @Resource
    private AssembleHelper  assembleHelper;
    @Resource
    private ApiKeyHelper apiKeyHelper;
    @Resource
    private UserMapper userMapper;

    //检查修改时可能存在的一些问题
    public void throwAmendQuestion(Post post,int user_id){
        if (post == null) {
            throw new ApiException(POST_NOT_FOUND);
        }
        /*
        还要加一个举报不允许改
        例:if (post.report==1){
            throw new ApiException(RESOURCE_reported);
        }
        */
        if (post.host!=user_id) {
            throw new ApiException(PERMISSION_NOT_ALLOWED);
        }
    }
    public void inBlackList(Post post, int user_id){
        if (post == null) {
            throw new ApiException(POST_NOT_FOUND);
        }
        User user=userMapper.selectById(post.host);
        if (idInArray(user.blacklist,user_id)!=-1) {
            throw new ApiException(POST_NOT_FOUND);
        }
    }
    public boolean isTitleValid(String title)
    {
        if(title==null||title.isEmpty())
            throw new ApiException(NULL_TITLE);
        if(title.length()>20)
            throw new ApiException(TITLE_TOO_LONG);
        return true;
    }
    public boolean isContentValid(String content)
    {
        if(content==null||content.isEmpty())
            throw new ApiException(NULL_CONTENT);
        if(content.length()>1000)
            throw new ApiException(CONTENT_TOO_LONG);
        return true;
    }
    //你加一下注释吧
    public String getPicture(MultipartFile[] pictures){
        String picture;

        //新建一个链表，因为后面要转成特定格式字符串所以用了ArrayNode
        ObjectMapper objectMapper = new ObjectMapper();
        ArrayNode array= objectMapper.createArrayNode();

        if(pictures!=null)
        {
            log.info(String.valueOf(pictures.length));
            if(pictures.length>9)//防止超9张
                throw new ApiException(PICTURE_TOO_LONG);
            int id;
            for (int i = 0; i < pictures.length; i++) {
                id = pictureHelper.storePicture(pictures[i]);//存储图片返回图片的索引，空的图片传回-1
                if(id==-1) continue;
                array.add(id);//把索引加进链表
            }

        }
        //把存有图片索引的链表转成字符串以在数据库中储存
        //格式是:[1,2,3,4,5]或者[](null)
        if(array.isEmpty()) picture="[]";//没图片就默认为空数组“[]”
        else picture=array.toString();
        return picture;
    }
    @Override
    public AjaxResult<String> uploadPost(UploadPostRequest req, String apiKey)
    {
        if(isContentValid(req.content)&&isTitleValid(req.title))
        {
            Post post=new Post();
            post.content=req.content;
            post.title=req.title;
            post.host=req.user_id;
            post.depth=1;
            post.hidden=false;
            post.ispublic=req.ispublic;
            post.anonymity=req.anonymity;
            post.likelist="[]";
            post.subcomment="[]";
            post.picture=getPicture(req.pictures);
            post.date= Instant.now().toEpochMilli();
            postMapper.insert(post);
            return AjaxResult.success(null);
        }
        return  AjaxResult.fail(null);
    }
    @Override
    public AjaxResult<List<Post>> getPostOfUser(int target_id, String apiKey)
    {
        int user_id = apiKeyHelper.getUserId(apiKey);
        QueryWrapper<Post> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("host",target_id);
        List<Post> posts = postMapper.selectList(queryWrapper);
        User user=userMapper.selectById(target_id);
        if(posts.isEmpty()||(idInArray(user.blacklist,user_id)!=-1)) return AjaxResult.success(null);
        for(int i=0;i<posts.size();i++)
        {
            //泥马勒戈壁的JAVA连委托都没有
            if(!posts.get(i).ispublic&&user_id!=posts.get(i).host)
            {
                posts.remove(i);
                i--;
                continue;
            }
            assembleHelper.assemble(posts.get(i),user_id,false);
        }
        return AjaxResult.success(posts);
    }
    @Override
    public AjaxResult<Post> getPostInfo(GetPostInfoRequest req, String apiKey)
    {
        QueryWrapper<Post> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("id",req.post_id);
        Post post=postMapper.selectOne(queryWrapper);
        if(post==null)
            throw new ApiException(POST_NOT_FOUND);
        if((!post.ispublic&&req.user_id!=post.host))
            throw new ApiException(POST_NOT_FOUND);
        assembleHelper.assemble(post,req.user_id,true);
        return AjaxResult.success(post);
    }
    @Override
    public void updatePost(UpdatePostRequest req, String apiKey) {
        req.setUser_id(apiKeyHelper.parseApiKey(apiKey));
        if(isTitleValid(req.getNewTitle())&&isContentValid(req.getNewContent())) {
            Post post = Constant.postCache.tryFindById(req.getPost_id());
            throwAmendQuestion(post, req.getUser_id());//检查修改时可能存在的一些问题
            //修改
            post.title=req.getNewTitle();
            post.content=req.getNewContent();
            post.picture=getPicture(req.pictures);
            post.anonymity=req.isAnonymity();
            post.ispublic=req.isPublic();
            Constant.postCache.tryUpdate(post);
        }
    }
    @Override
    public void postLike(int post_id, String apiKey) {
        int user_id=apiKeyHelper.parseApiKey(apiKey);
        Post post = Constant.postCache.tryFindById(post_id);
        User user = userMapper.selectById(user_id);
        if (user==null)
            throw new ApiException(USER_NOT_FOUND);
        inBlackList(post,user_id);
        StringHelper.log(user_id);
        StringHelper.log(post.host);
        if(user_id==post.host)
            throw new ApiException(HOST_ADD_LIKE);
        log.info("user");
        if(idInArray(post.likelist,user_id)!=-1)
        {
            LikeEvent event=new LikeEvent(Constant.eventHandler,false,user_id,post_id);
            Constant.eventHandler.addEvent(event);
        }
        else
        {
            LikeEvent event=new LikeEvent(Constant.eventHandler,true,user_id,post_id);
            Constant.eventHandler.addEvent(event);
        }
        //Constant.postCache.tryUpdate(post);
        //事件处理器已经自动update了
    }

    @Override
    public void postDelete(int post_id, String apiKey) {
        int user_id=apiKeyHelper.parseApiKey(apiKey);
        Post post = Constant.postCache.tryFindById(post_id);
        throwAmendQuestion(post,user_id);
        Constant.postCache.tryDelete(post);
    }

    @Override
    public AjaxResult<List<Post>> getRecommended(int userId)
    {
        List<Post> posts=(Constant.pushCache.getRecommended(userId));
        for(int i=0;i<posts.size();i++)
        {
            assembleHelper.assemble(posts.get(i),userId,false);
        }
        return AjaxResult.success(posts);
    }
}
