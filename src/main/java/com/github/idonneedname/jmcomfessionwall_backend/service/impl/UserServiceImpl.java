package com.github.idonneedname.jmcomfessionwall_backend.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.idonneedname.jmcomfessionwall_backend.entity.AdminWhiteList;
import com.github.idonneedname.jmcomfessionwall_backend.entity.Picture;
import com.github.idonneedname.jmcomfessionwall_backend.entity.User;
import com.github.idonneedname.jmcomfessionwall_backend.exception.ApiException;
import com.github.idonneedname.jmcomfessionwall_backend.helper.*;
import com.github.idonneedname.jmcomfessionwall_backend.mapper.AdminWhiteListMapper;
import com.github.idonneedname.jmcomfessionwall_backend.mapper.PictureMapper;
import com.github.idonneedname.jmcomfessionwall_backend.mapper.UserMapper;
import com.github.idonneedname.jmcomfessionwall_backend.request.*;
import com.github.idonneedname.jmcomfessionwall_backend.result.AjaxResult;
import com.github.idonneedname.jmcomfessionwall_backend.service.UserService;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static com.github.idonneedname.jmcomfessionwall_backend.constant.ExceptionEnum.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
    @Resource
    private final UserMapper userMapper;
    @Resource
    private final PictureHelper pictureHelper;
    @Resource
    private final PictureMapper pictureMapper;
    @Resource
    private final AdminWhiteListMapper  adminWhiteListMapper;
    @Resource
    AssembleHelper  assembleHelper;
    @Resource
    StringHelper stringHelper;
    @Resource
    ApiKeyHelper apiKeyHelper;
    @Override
    public AjaxResult<User> login(LoginRequest req){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username",req.getUsername()).eq("password",req.getPassword());

        User user = userMapper.selectOne(queryWrapper);
        if(user==null){
            throw new ApiException(WRONG_USERNAME_OR_PASSWORD);
        }
        else {
             user._blacklist= ArrayNodeHelper.translateToArray(user.blacklist);
             QueryWrapper<Picture> pictureQueryWrapper = new QueryWrapper<>();
             pictureQueryWrapper.eq("id",user.pictureref);
             user.portrait=pictureMapper.selectOne(pictureQueryWrapper);
             if(user.portrait!=null)
                 user.portrait.pixel=pictureHelper.getPixels(user.pictureref);
             AjaxResult<User> response=AjaxResult.success(user);
             String api=apiKeyHelper.trySet(user.id);
             response.setMsg(api);
             return response;
        }
    }
    public boolean isUserNameValid(String username){
        return username.length() <= 20;
    }
    public boolean isPassWordValid(String password) {
        boolean num=false,letter=false;
        if(password.length()>30)
            return false;
        for(int i=0;i<password.length();i++)
        {
            int asc=(int)password.charAt(i);
            if((asc>=(int)'A'&&asc<=(int)'Z')||(asc>=(int)'a'&&asc<=(int)'z'))
                letter=true;
            else
            if(asc>=(int)'0'&&asc<=(int)'9')
                num=true;
            else
                return false;
        }
        return num && letter;
    }
    public boolean isTypeValid(RegisterRequest req){
        if(req.type==1)
            return true;
        QueryWrapper<AdminWhiteList> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username",req.username);
        AdminWhiteList adminWhiteList = adminWhiteListMapper.selectOne(queryWrapper);
        return adminWhiteList != null;
    }
    @Override
    public AjaxResult<String>  register(RegisterRequest req){
           if(isUserNameValid(req.getUsername()))
           {
               if(isPassWordValid(req.getPassword()))
               {
                   if(isTypeValid(req))//判断是否可以是管理员，普通成员直接true
                   {
                        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
                        queryWrapper.eq("username",req.getUsername());
                        User other = userMapper.selectOne(queryWrapper);
                        if(other!=null)
                        {
                            throw new ApiException(NAME_TAKEN);
                        }
                        User user=new User();
                        user.username=req.username;
                        user.password=req.password;
                        user.type=req.type;
                        user.name=req.name;
                        user.blacklist="[]";//初始化一下
                        user.pictureref=-1;//无头像，默认为-1
                        userMapper.insert(user);
                        return AjaxResult.success(null);
                   }
                   else
                       throw new ApiException(NOT_BELONG_TO_ADMIN);
               }
               else
                   throw new ApiException(INVALID_PASSWORD);
           }
           return AjaxResult.fail(USERNAME_TOO_LONG);
    }
    @Override
    public AjaxResult<String> amendName(AmendNameRequest req, String apiKey)
    {
        if(!apiKeyHelper.isVaildApiKey(req.user_id,apiKey))
            throw new ApiException(INVALID_APIKEY);
        if(isUserNameValid(req.newname))
        {
            QueryWrapper<User> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("id",req.user_id);
            User user = userMapper.selectOne(queryWrapper);
            if(user==null)
                throw new ApiException(USER_NOT_FOUND);
            user.name=req.newname;
            userMapper.update(user,queryWrapper);
            return AjaxResult.success(null);
        }
        return AjaxResult.fail(USERNAME_TOO_LONG);
    }
    @Override
    public AjaxResult<String> amendPassword(AmendPasswordRequest req, String apiKey)
    {
        if(!apiKeyHelper.isVaildApiKey(req.user_id,apiKey))
            throw new ApiException(INVALID_APIKEY);
        if(isPassWordValid(req.newpassword))
        {
            QueryWrapper<User> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("id",req.user_id);
            User user = userMapper.selectOne(queryWrapper);

            if(user==null)
                throw new ApiException(USER_NOT_FOUND);
            if(!user.password.equals(req.originpassword))
                throw new ApiException(ORIGINAL_PASSWORD_ERROR);

            user.password=req.newpassword;
            userMapper.update(user,queryWrapper);
            return AjaxResult.success(null);
        }
        else
            throw new ApiException(INVALID_PASSWORD);
    }
    @Override
    public AjaxResult<User> getUserInformation(GetUserInfoRequest req,String apiKey)
    {
        //log.info(apiKey);
        if(!apiKeyHelper.isVaildApiKey(req.user_id,apiKey))
            throw new ApiException(INVALID_APIKEY);
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id",req.target_id);
        User user = userMapper.selectOne(queryWrapper);
        if(user==null)
            throw new ApiException(USER_NOT_FOUND);
        else
        {
            assembleHelper.assemble(user);
            return AjaxResult.success(user);
        }

    }
    @Override
    public AjaxResult<String> uploadPortrait(UploadPortraitRequest req,String apiKey)
    {
        StringHelper.log(req.user_id);
        if(!apiKeyHelper.isVaildApiKey(req.user_id,apiKey))
            throw new ApiException(INVALID_APIKEY);
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id",req.user_id);
        User user = userMapper.selectOne(queryWrapper);
        if(user==null)
            throw new ApiException(USER_NOT_FOUND);
        user.pictureref=pictureHelper.storeOne(req.picture);
        userMapper.update(user,queryWrapper);
        return AjaxResult.success(null);
    }
    @Override
    public AjaxResult<String> addBlackList(BlackListRequest req, String apiKey)
    {
        if(!apiKeyHelper.isVaildApiKey(req.user_id,apiKey))
            throw new ApiException(INVALID_APIKEY);
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id",req.user_id);
        User user = userMapper.selectOne(queryWrapper);
        if(user==null)
            throw new ApiException(USER_NOT_FOUND);

        if(user.blacklist==null)
            user.blacklist="[]";
        user.blacklist=ArrayNodeHelper.add(user.blacklist,req.target_id);
        userMapper.update(user,queryWrapper);
        return AjaxResult.success(null);
    }
    public AjaxResult<String> deleteBlackList(BlackListRequest req, String apiKey)
    {
        if(!apiKeyHelper.isVaildApiKey(req.user_id,apiKey))
            throw new ApiException(INVALID_APIKEY);
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id",req.user_id);
        User user = userMapper.selectOne(queryWrapper);
        if(user==null)
            throw new ApiException(USER_NOT_FOUND);
        if(user.blacklist==null)
            user.blacklist="[]";
        user.blacklist=ArrayNodeHelper.delete(user.blacklist,req.target_id);
        userMapper.update(user,queryWrapper);
        return AjaxResult.success(null);
    }


}
