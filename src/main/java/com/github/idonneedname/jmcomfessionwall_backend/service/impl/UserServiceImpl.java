package com.github.idonneedname.jmcomfessionwall_backend.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.idonneedname.jmcomfessionwall_backend.entity.Picture;
import com.github.idonneedname.jmcomfessionwall_backend.entity.User;
import com.github.idonneedname.jmcomfessionwall_backend.exception.ApiException;
import com.github.idonneedname.jmcomfessionwall_backend.helper.ApiKeyHelper;
import com.github.idonneedname.jmcomfessionwall_backend.helper.ArrayNodeHelper;
import com.github.idonneedname.jmcomfessionwall_backend.helper.PictureHelper;
import com.github.idonneedname.jmcomfessionwall_backend.mapper.PictureMapper;
import com.github.idonneedname.jmcomfessionwall_backend.mapper.UserMapper;
import com.github.idonneedname.jmcomfessionwall_backend.request.LoginRequest;
import com.github.idonneedname.jmcomfessionwall_backend.request.RegisterRequest;
import com.github.idonneedname.jmcomfessionwall_backend.result.AjaxResult;
import com.github.idonneedname.jmcomfessionwall_backend.service.UserService;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.github.idonneedname.jmcomfessionwall_backend.constant.ExceptionEnum.*;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    @Resource
    private final UserMapper userMapper;
    @Resource
    private final PictureHelper pictureHelper;
    @Resource
    private final PictureMapper pictureMapper;
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
             String api=ApiKeyHelper.genApiKey(user.id);
             ApiKeyHelper.trySet(user.id,api);
             response.setMsg(api);
             return response;
        }
    }
    public boolean isUserNameValid(String username){
           if(username.length()>20)
               throw new ApiException(USERNAME_TOO_LONG);
           return true;
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
        if(num&&letter)
            return true;
        else
            return false;
    }
    public boolean isTypeValid(RegisterRequest req){
        return true;//这方面还要再设计一下，先放个方法在这里
    }
    public AjaxResult<String>  register(RegisterRequest req){
           if(isUserNameValid(req.getUsername()))
           {
               if(isPassWordValid(req.getPassword()))
               {
                   if(isTypeValid(req))
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
               }
               else
                   throw new ApiException(INVALID_PASSWORD);
           }
           return AjaxResult.fail(null);
    }

}
