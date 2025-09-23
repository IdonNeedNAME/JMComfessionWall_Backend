package com.github.idonneedname.jmcomfessionwall_backend.service;

import com.github.idonneedname.jmcomfessionwall_backend.entity.User;
import com.github.idonneedname.jmcomfessionwall_backend.request.*;
import com.github.idonneedname.jmcomfessionwall_backend.result.AjaxResult;

public interface UserService {
    public AjaxResult<User> login(LoginRequest req);
    public AjaxResult<String> register(RegisterRequest req);
    public AjaxResult<String> amend_Name(AmendNameRequest req,String apiKey);
    public AjaxResult<String> amend_Password(AmendPasswordRequest req,String apiKey);
    public AjaxResult<User> getUserInformation(GetUserInfoRequest req, String apiKey);
    public AjaxResult<String> uploadPortrait(UploadPortraitRequest req, String apiKey);
    public AjaxResult<String> addBlackList(BlackListRequest req, String apiKey);
    public AjaxResult<String> deleteBlackList(BlackListRequest req, String apiKey);
}
