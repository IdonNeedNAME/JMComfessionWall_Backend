package com.github.idonneedname.jmcomfessionwall_backend.controler;

import com.github.idonneedname.jmcomfessionwall_backend.entity.User;
import com.github.idonneedname.jmcomfessionwall_backend.request.*;
import com.github.idonneedname.jmcomfessionwall_backend.result.AjaxResult;
import com.github.idonneedname.jmcomfessionwall_backend.service.UserService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/user")
@Slf4j
public class UserControler {
    @Resource
    private UserService userService;

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
    @PostMapping("/amend/portrait")
    public AjaxResult<String> amendPortrait(@RequestParam int user_id,@RequestParam MultipartFile picture, @RequestHeader("X-API-KEY") String api){
        UploadPortraitRequest req=new UploadPortraitRequest();
        req.user_id=user_id;
        req.picture=picture;
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
}
