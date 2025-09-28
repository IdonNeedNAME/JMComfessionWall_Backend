package com.github.idonneedname.jmcomfessionwall_backend.service;

import com.github.idonneedname.jmcomfessionwall_backend.entity.User;
import com.github.idonneedname.jmcomfessionwall_backend.request.RegAndLog.LoginRequest;
import com.github.idonneedname.jmcomfessionwall_backend.request.RegAndLog.RegisterRequest;
import com.github.idonneedname.jmcomfessionwall_backend.request.user.*;
import com.github.idonneedname.jmcomfessionwall_backend.result.AjaxResult;

public interface UserService {
    public AjaxResult<User> login(LoginRequest req);
    public AjaxResult<String> register(RegisterRequest req);
    public AjaxResult<String> amendName(AmendNameRequest req, String apiKey);
    public AjaxResult<String> amendPassword(AmendPasswordRequest req, String apiKey);
    AjaxResult<User> getUserInformation(int target_id, String apiKey);
    public AjaxResult<String> uploadPortrait(UploadPortraitRequest req, String apiKey);
    public AjaxResult<String> addBlackList(BlackListRequest req, String apiKey);
    public AjaxResult<String> deleteBlackList(BlackListRequest req, String apiKey);
}
