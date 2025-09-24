package com.github.idonneedname.jmcomfessionwall_backend.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.idonneedname.jmcomfessionwall_backend.entity.Post;
import com.github.idonneedname.jmcomfessionwall_backend.exception.ApiException;
import com.github.idonneedname.jmcomfessionwall_backend.helper.ApiKeyHelper;
import com.github.idonneedname.jmcomfessionwall_backend.helper.ArrayNodeHelper;
import com.github.idonneedname.jmcomfessionwall_backend.helper.PictureHelper;
import com.github.idonneedname.jmcomfessionwall_backend.helper.StringHelper;
import com.github.idonneedname.jmcomfessionwall_backend.mapper.PictureMapper;
import com.github.idonneedname.jmcomfessionwall_backend.mapper.PostMapper;
import com.github.idonneedname.jmcomfessionwall_backend.request.UploadPostRequest;
import com.github.idonneedname.jmcomfessionwall_backend.result.AjaxResult;
import com.github.idonneedname.jmcomfessionwall_backend.service.PostService;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import static com.github.idonneedname.jmcomfessionwall_backend.constant.ExceptionEnum.*;


@Service
@RequiredArgsConstructor
@Slf4j
public class PostServiceImpl implements PostService {
    @Resource
    private PostMapper postMapper;
    @Resource
    private PictureHelper pictureHelper;
    @Autowired
    private PictureMapper pictureMapper;
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
        if(content.length()>20)
            throw new ApiException(CONTENT_TOO_LONG);
        return true;
    }
    @Override
    public AjaxResult<String> uploadPost(UploadPostRequest req,String apiKey)
    {
        if(!ApiKeyHelper.isVaildApiKey(req.user_id,apiKey))
            throw new ApiException(INVALID_APIKEY);
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
            ObjectMapper objectMapper = new ObjectMapper();
            ArrayNode array= objectMapper.createArrayNode();
            if(req.pictures!=null)
            {
                int id;
                for(int i=0;i<req.pictures.length;i++)
                {
                    id=pictureHelper.storeOne(req.pictures[i]);
                    array.add(id);
                    StringHelper.log(id);
                }
            }
            if(array.isEmpty()) post.picture="[]";
            else post.picture=array.toString();
            postMapper.insert(post);
            return AjaxResult.success(null);
        }
        return  AjaxResult.fail(null);
    }

}





