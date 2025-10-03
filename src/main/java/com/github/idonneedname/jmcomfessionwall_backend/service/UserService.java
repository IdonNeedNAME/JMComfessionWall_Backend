package com.github.idonneedname.jmcomfessionwall_backend.service;

import com.github.idonneedname.jmcomfessionwall_backend.Response.UserInfoResponse;
import com.github.idonneedname.jmcomfessionwall_backend.entity.User;
import com.github.idonneedname.jmcomfessionwall_backend.request.RegAndLog.LoginRequest;
import com.github.idonneedname.jmcomfessionwall_backend.request.RegAndLog.RegisterRequest;
import com.github.idonneedname.jmcomfessionwall_backend.request.user.AmendNameRequest;
import com.github.idonneedname.jmcomfessionwall_backend.request.user.AmendPasswordRequest;
import com.github.idonneedname.jmcomfessionwall_backend.request.user.BlackListRequest;
import com.github.idonneedname.jmcomfessionwall_backend.request.user.UploadPortraitRequest;
import com.github.idonneedname.jmcomfessionwall_backend.result.AjaxResult;

import java.util.List;

public interface UserService {
    AjaxResult<User> login(LoginRequest req);
    AjaxResult<String> register(RegisterRequest req);
    AjaxResult<String> amendName(AmendNameRequest req, String apiKey);
    AjaxResult<String> amendPassword(AmendPasswordRequest req, String apiKey);
    AjaxResult<UserInfoResponse> getUserInformation(int target_id, String apiKey);
    AjaxResult<String> uploadPortrait(UploadPortraitRequest req, String apiKey);
    AjaxResult<String> addBlackList(BlackListRequest req, String apiKey);
    AjaxResult<String> deleteBlackList(BlackListRequest req, String apiKey);
    AjaxResult<List<Integer>> getBlackList(String apiKey);
}
