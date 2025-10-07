package com.github.idonneedname.jmcomfessionwall_backend.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.idonneedname.jmcomfessionwall_backend.Response.UserInfoResponse;
import com.github.idonneedname.jmcomfessionwall_backend.constant.Constant;
import com.github.idonneedname.jmcomfessionwall_backend.entity.AdminWhiteList;
import com.github.idonneedname.jmcomfessionwall_backend.entity.ApiKey;
import com.github.idonneedname.jmcomfessionwall_backend.entity.Picture;
import com.github.idonneedname.jmcomfessionwall_backend.entity.User;
import com.github.idonneedname.jmcomfessionwall_backend.exception.ApiException;
import com.github.idonneedname.jmcomfessionwall_backend.helper.*;
import com.github.idonneedname.jmcomfessionwall_backend.mapper.AdminWhiteListMapper;
import com.github.idonneedname.jmcomfessionwall_backend.mapper.ApiKeyMapper;
import com.github.idonneedname.jmcomfessionwall_backend.mapper.PictureMapper;
import com.github.idonneedname.jmcomfessionwall_backend.mapper.UserMapper;
import com.github.idonneedname.jmcomfessionwall_backend.request.RegAndLog.LoginRequest;
import com.github.idonneedname.jmcomfessionwall_backend.request.RegAndLog.RegisterRequest;
import com.github.idonneedname.jmcomfessionwall_backend.request.user.AmendNameRequest;
import com.github.idonneedname.jmcomfessionwall_backend.request.user.AmendPasswordRequest;
import com.github.idonneedname.jmcomfessionwall_backend.request.user.BlackListRequest;
import com.github.idonneedname.jmcomfessionwall_backend.request.user.UploadPortraitRequest;
import com.github.idonneedname.jmcomfessionwall_backend.result.AjaxResult;
import com.github.idonneedname.jmcomfessionwall_backend.service.UserService;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

import static com.github.idonneedname.jmcomfessionwall_backend.constant.ExceptionEnum.*;
import static com.github.idonneedname.jmcomfessionwall_backend.helper.ArrayNodeHelper.idInArray;
import static com.github.idonneedname.jmcomfessionwall_backend.helper.ArrayNodeHelper.translateToArray;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
    @Resource
    private UserMapper userMapper;
    @Resource
    private PictureHelper pictureHelper;
    @Resource
    private PictureMapper pictureMapper;
    @Resource
    private AdminWhiteListMapper  adminWhiteListMapper;
    @Resource
    private ApiKeyMapper apiKeyMapper;
    @Resource
    AssembleHelper  assembleHelper;
    @Resource
    StringHelper stringHelper;
    @Resource
    ApiKeyHelper apiKeyHelper;
    @Resource
    FileStorageService  fileStorageService;

    @Override
    public AjaxResult<User> login(LoginRequest req){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username",req.getUsername()).eq("password",req.getPassword());
        User user = userMapper.selectOne(queryWrapper);
        log.info(req.username);
        log.info(req.password);
        if(user==null){
            throw new ApiException(WRONG_USERNAME_OR_PASSWORD);
        }
        else {
            assembleHelper.assemble(user);
            AjaxResult<User> response=AjaxResult.success(user);
            String api=apiKeyHelper.genKey(user.id);
            ApiKey apiKey=apiKeyMapper.selectById(user.id);
            if (apiKey==null) {
                apiKey=new ApiKey();
                apiKey.apikey = api;
                apiKey.id = user.id;
                apiKeyMapper.insert(apiKey);
            }else {
                apiKey.apikey = api;
                apiKeyMapper.updateById(apiKey);
            }
            response.setMsg(api);
            Constant.sessionCache.resetCache(user.id);
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
        if (!isUserNameValid(req.getUsername()))
            return AjaxResult.fail(USERNAME_TOO_LONG);
        if (req.getUsername()==null || req.getUsername().isEmpty())
            return AjaxResult.fail(NULL_USERNAME);
        if (!isPassWordValid(req.getPassword()))
            throw new ApiException(INVALID_PASSWORD);
        if (!isTypeValid(req))//判断是否可以是管理员，普通成员直接true
            throw new ApiException(NOT_BELONG_TO_ADMIN);
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username",req.getUsername());
        User other = userMapper.selectOne(queryWrapper);
        if(other!=null)
            throw new ApiException(NAME_TAKEN);
        User user=new User();
        user.username=req.username;
        user.password=req.password;
        user.type=req.type;
        user.name=req.name;
        user.blacklist="[]";//初始化一下
        user.anonymousposts="[]";
        user.pictureref=-1;//无头像，默认为-1
        userMapper.insert(user);
        return AjaxResult.success();
    }

    @Override
    public AjaxResult<String> amendName(AmendNameRequest req, String apiKey)
    {
        log.info(req.getNewname());
        int user_id= apiKeyHelper.getUserId(apiKey);
        if(isUserNameValid(req.getNewname()))
        {
            User user = userMapper.selectById(user_id);
            user.name=req.getNewname();
            userMapper.updateById(user);
            return AjaxResult.success();
        }
        return AjaxResult.fail(USERNAME_TOO_LONG);
    }
    @Override
    public AjaxResult<String> amendPassword(AmendPasswordRequest req, String apiKey)
    {
        int user_id= apiKeyHelper.getUserId(apiKey);
        User user = userMapper.selectById(user_id);
        if(!user.password.equals(req.getOriginpassword()))
            throw new ApiException(ORIGINAL_PASSWORD_ERROR);
        if(!isPassWordValid(req.getNewpassword()))
            throw new ApiException(INVALID_PASSWORD);
        user.password=req.getNewpassword();
        userMapper.updateById(user);
        ApiKey api=apiKeyMapper.selectById(user_id);
        api.setApikey(apiKeyHelper.genKey(user.id));
        apiKeyMapper.updateById(api);
        return AjaxResult.success();
    }
    @Override
    public AjaxResult<UserInfoResponse> getUserInformation(int target_id, String apiKey)
    {
        log.info(apiKey);
        int user_id=apiKeyHelper.getUserId(apiKey);
        log.info("user_id:"+user_id);
        User user = userMapper.selectById(target_id);
        log.info("tar:"+target_id);
        if(user==null)
            throw new ApiException(USER_NOT_FOUND);
        else
        {
            assembleHelper.assemble(user);
            UserInfoResponse response=new UserInfoResponse();
            response.setId(user_id);
            response.setUsername(user.username);
            response.setName(user.name);
            response.setPortrait(user.portrait);
            response.setType(user.type);
            return AjaxResult.success(response);
        }
    }
    @Override
    public AjaxResult<String> uploadPortrait(UploadPortraitRequest req, String apiKey)
    {
        int user_id= apiKeyHelper.getUserId(apiKey);
        User user = userMapper.selectById(user_id);
        user.pictureref=pictureHelper.storePicture(req.getPicture());
        userMapper.updateById(user);
        Picture pic=new Picture();
        assembleHelper.assemble(pic,user.pictureref);
        return AjaxResult.success(pic.url);
    }
    @Override
    public AjaxResult<String> addBlackList(BlackListRequest req, String apiKey)
    {
        int user_id= apiKeyHelper.getUserId(apiKey);
        if(req.getTarget_id()==user_id)
            throw new ApiException(MEIDIQI);
        User blacklistedUser=userMapper.selectById(req.getTarget_id());
        if(blacklistedUser==null)
            throw new ApiException(USER_NOT_FOUND);

        User user = userMapper.selectById(user_id);
        if(user.blacklist==null)
            user.blacklist="[]";
        if(idInArray(user.blacklist,req.getTarget_id())!=-1)
            throw new ApiException(HAS_BEEN_IN_BLACKLIST);
        user.blacklist=ArrayNodeHelper.add(user.blacklist,req.getTarget_id());
        userMapper.updateById(user);
        return AjaxResult.success();
    }
    public AjaxResult<String> deleteBlackList(BlackListRequest req, String apiKey)
    {
        int user_id= apiKeyHelper.getUserId(apiKey);
        User blacklistedUser=userMapper.selectById(req.getTarget_id());
        if(blacklistedUser==null)
            throw new ApiException(USER_NOT_FOUND);
        User user = userMapper.selectById(user_id);
        if(user.blacklist==null)
            user.blacklist="[]";
        if (idInArray(user.blacklist,req.getTarget_id())==-1)
            throw new ApiException(IS_NOT_IN_BLACKLIST);
        user.blacklist=ArrayNodeHelper.delete(user.blacklist,req.getTarget_id());
        userMapper.updateById(user);
        return AjaxResult.success();
    }

    @Override
    public AjaxResult<List<Integer>> getBlackList(int userId,String apiKey) {
        int user_id= apiKeyHelper.getUserId(apiKey);
        User user2 = userMapper.selectById(user_id);
        if(userId!=user_id&&user2.type!=2)
        {
            throw new ApiException(PERMISSION_NOT_ALLOWED);
        }
        User user = userMapper.selectById(userId);
        return AjaxResult.success(translateToArray(user.blacklist));
    }


}
