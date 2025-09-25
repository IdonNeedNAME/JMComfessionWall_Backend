package com.github.idonneedname.jmcomfessionwall_backend.controler;

import com.github.idonneedname.jmcomfessionwall_backend.entity.Post;
import com.github.idonneedname.jmcomfessionwall_backend.entity.User;
import com.github.idonneedname.jmcomfessionwall_backend.request.*;
import com.github.idonneedname.jmcomfessionwall_backend.result.AjaxResult;
import com.github.idonneedname.jmcomfessionwall_backend.service.impl.PostServiceImpl;
import com.github.idonneedname.jmcomfessionwall_backend.service.impl.UserServiceImpl;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@Slf4j
public class UserController {
    @Resource
    private UserServiceImpl userService;
    @Resource
    private PostServiceImpl postService;

    @PostMapping("/register")
    public AjaxResult<String> register(@RequestBody RegisterRequest req){
        return userService.register(req);
    }
    @PostMapping("/login")
    public AjaxResult<com.github.idonneedname.jmcomfessionwall_backend.entity.User> login(@RequestBody LoginRequest req){
        return userService.login(req);
    }
    @PatchMapping("/amend/name")
    public AjaxResult<String> amendName(@RequestBody AmendNameRequest req,@RequestHeader("X-API-KEY") String api){
        return userService.amend_Name(req,api);
    }
    @PatchMapping("/amend/password")
    public AjaxResult<String> amendPassword(@RequestBody AmendPasswordRequest req, @RequestHeader("X-API-KEY") String api){
        return userService.amend_Password(req,api);
    }
    @PostMapping(
            value = "/amend/portrait",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public AjaxResult<String> amendPortrait(UploadPortraitRequest req, @RequestHeader("X-API-KEY") String api){
        return userService.uploadPortrait(req,api);
    }
    @PostMapping("/blacklist")
    public AjaxResult<String> addBlacklist(@RequestBody BlackListRequest req, @RequestHeader("X-API-KEY") String api){
        return userService.addBlackList(req,api);
    }
    @GetMapping("")
    public AjaxResult<User> addBlacklist(@RequestBody GetUserInfoRequest req, @RequestHeader("X-API-KEY") String api){
        return userService.getUserInformation(req,api);
    }
    @DeleteMapping("/deblacklist")
    public AjaxResult<String> deleteBlacklist(@RequestBody BlackListRequest req, @RequestHeader("X-API-KEY") String api){
        return userService.deleteBlackList(req,api);
    }
    @GetMapping("/post")
    public AjaxResult<List<Post>> getPostsOfUser(@RequestBody GetPostOfUserRequest req, @RequestHeader("X-API-KEY") String api){
        return postService.getPostOfUser(req,api);
    }
}
