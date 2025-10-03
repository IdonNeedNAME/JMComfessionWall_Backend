package com.github.idonneedname.jmcomfessionwall_backend.controler;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.idonneedname.jmcomfessionwall_backend.Response.UserInfoResponse;
import com.github.idonneedname.jmcomfessionwall_backend.constant.Constant;
import com.github.idonneedname.jmcomfessionwall_backend.entity.Comment;
import com.github.idonneedname.jmcomfessionwall_backend.entity.Post;
import com.github.idonneedname.jmcomfessionwall_backend.helper.ApiKeyHelper;
import com.github.idonneedname.jmcomfessionwall_backend.helper.AssembleHelper;
import com.github.idonneedname.jmcomfessionwall_backend.mapper.CommentMapper;
import com.github.idonneedname.jmcomfessionwall_backend.mapper.PostMapper;
import com.github.idonneedname.jmcomfessionwall_backend.request.RegAndLog.LoginRequest;
import com.github.idonneedname.jmcomfessionwall_backend.request.RegAndLog.RegisterRequest;
import com.github.idonneedname.jmcomfessionwall_backend.request.user.AmendNameRequest;
import com.github.idonneedname.jmcomfessionwall_backend.request.user.AmendPasswordRequest;
import com.github.idonneedname.jmcomfessionwall_backend.request.user.BlackListRequest;
import com.github.idonneedname.jmcomfessionwall_backend.request.user.UploadPortraitRequest;
import com.github.idonneedname.jmcomfessionwall_backend.result.AjaxResult;
import com.github.idonneedname.jmcomfessionwall_backend.service.impl.PostServiceImpl;
import com.github.idonneedname.jmcomfessionwall_backend.service.impl.UserServiceImpl;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;

@RestController
@RequestMapping("/api/user")
@Slf4j
public class UserController {
    @Resource
    private UserServiceImpl userService;
    @Resource
    private PostServiceImpl postService;
    @Autowired
    private ApiKeyHelper apiKeyHelper;
    @Autowired
    private PostMapper postMapper;
    @Autowired
    private AssembleHelper assembleHelper;
    @Resource
    private CommentMapper commentMapper;

    @PostMapping("/register")
    public AjaxResult<String> register(@RequestBody RegisterRequest req){
        return userService.register(req);
    }
    @PostMapping("/login")
    public AjaxResult<com.github.idonneedname.jmcomfessionwall_backend.entity.User> login(@RequestBody LoginRequest req){
        return userService.login(req);
    }
    @PatchMapping("/amend/name")
    public AjaxResult<String> amendName(@RequestBody AmendNameRequest req, @RequestHeader("X-API-KEY") String api){
        return userService.amendName(req,api);
    }
    @PatchMapping("/amend/password")
    public AjaxResult<String> amendPassword(@RequestBody AmendPasswordRequest req, @RequestHeader("X-API-KEY") String api){
        return userService.amendPassword(req,api);
    }
    @PostMapping(
            value = "/amend/portrait",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public AjaxResult<String> amendPortrait(UploadPortraitRequest req, @RequestHeader("X-API-KEY") String api){
        return userService.uploadPortrait(req,api);
    }
    @PostMapping("/blacklist")
    public AjaxResult<String> getUserInfo(@RequestBody BlackListRequest req, @RequestHeader("X-API-KEY") String api){
        return userService.addBlackList(req,api);
    }
    @GetMapping("/{id}")
    public AjaxResult<UserInfoResponse> getUserInfo(@PathVariable("id") int id, @RequestHeader("X-API-KEY") String api){
        return userService.getUserInformation(id,api);
    }
    @DeleteMapping("/deblacklist")
    public AjaxResult<String> deleteBlacklist(@RequestBody BlackListRequest req, @RequestHeader("X-API-KEY") String api){
        return userService.deleteBlackList(req,api);
    }
    @GetMapping("/{userId}/posts")
    public AjaxResult<List<Post>> getPostsOfUser(@PathVariable("userId") int userId, @RequestHeader("X-API-KEY") String api){
        return postService.getPostOfUser(userId,api);
    }
    @GetMapping("/black")
    public AjaxResult<List<Integer>> getBlackList(@RequestHeader("X-API-KEY") String api){
        return userService.getBlackList(api);
    }
    @PostMapping("/pic/{id}")//不用管跑测试用的
    public AjaxResult<String> fuckyouJAVA(@PathVariable int id,@RequestHeader("X-API-KEY") String api){
         Post post= Constant.postCache.tryFindById(id);
         post.likes+=999;
         Constant.postCache.tryUpdate(post);
        return AjaxResult.success("ssss");
    }
}
