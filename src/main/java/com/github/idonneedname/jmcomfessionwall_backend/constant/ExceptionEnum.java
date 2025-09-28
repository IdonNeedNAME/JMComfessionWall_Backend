package com.github.idonneedname.jmcomfessionwall_backend.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;


//记录错误类型
@Getter
@AllArgsConstructor
public enum ExceptionEnum {
    //user类错误
    WRONG_USERNAME_OR_PASSWORD(1001, "用户名或密码错误"),
    RESOURCE_NOT_FOUND(1002, "资源不存在"),
    INVALID_PASSWORD(1003, "密码格式错误"),
    PERMISSION_NOT_ALLOWED(1004, "权限不足"),
    NAME_TAKEN(1005, "用户名已被占用"),
    USERNAME_TOO_LONG(1006, "用户名过长"),
    NOT_BELONG_TO_ADMIN(1007, "不在管理员白名单内"),
    INVALID_APIKEY(1008,"APIKEY错误"),
    USER_NOT_FOUND(1009,"用户不存在"),
    NULL_USERNAME(1010,"用户名能为空"),
    OVERTIME_LOGIN(1011,"登录超时"),
    BLACKLIST_USER(1012,"你已被拉黑"),
    MEIDIQI(1013,"不能拉黑自己"),
    //post类错误
    HOST_ADD_LIKE(2000,"知道你很喜欢你的帖子了，不要再点了啦"),
    POST_NOT_FOUND(1002, "资源不存在"),
    COMMENT_NOT_FOUND(1002, "资源不存在"),
    CONTENT_TOO_LONG(2003, "帖子内容过长"),
    COMMENT_TOO_LONG(2004, "评论过长"),
    TITLE_TOO_LONG(2005, "标题内容过长"),
    PICTURE_TOO_LONG(2006, "图片上限九张"),
    NULL_TITLE(1010,"标题不能为空"),
    NULL_CONTENT(1010,"内容不能为空"),
    ORIGINAL_PASSWORD_ERROR(1011,"原密码错误"),

    //post审核状态
    RESOURCE_REPORTED(2006, "该帖子被举报，正在审核中"),
    RESOURCE_HAVE_BEEN_CHECKED(200, "该帖子已审核通过"),
    RESOURCE_NORMAL(200, null),



    //举报
    REPORT_NOT_FOUND(3001,"举报不存在"),
    //其他
    NOT_FOUND_ERROR(200404, HttpStatus.NOT_FOUND.getReasonPhrase()),
    INVALID_PARAMETER(200000, "参数错误"),
    SERVER_ERROR(200500, "系统错误, 请稍后重试"),
    ;

    private final Integer errorCode;
    private final String errorMsg;

}