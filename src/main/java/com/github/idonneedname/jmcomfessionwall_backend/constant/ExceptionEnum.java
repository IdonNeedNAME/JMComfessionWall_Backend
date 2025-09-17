package com.github.idonneedname.jmcomfessionwall_backend.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ExceptionEnum {
    WRONG_USERNAME_OR_PASSWORD(1001, "用户名或密码错误"),
    RESOURCE_NOT_FOUND(1002, "资源不存在"),
    INVALID_PASSWORD(1003, "密码格式错误"),
    PERMISSION_NOT_ALLOWED(1004, "权限不足"),
    NAME_TAKEN(1005, "用户名已被占用"),
    USERNAME_TOO_LONG(1005, "用户名过长"),


    POST_NOT_FOUND(1002, "资源不存在"),
    COMMENT_NOT_FOUND(1002, "资源不存在"),
    CONTENT_TOO_LONG(2003, "帖子内容过长"),
    COMMENT_TOO_LONG(2004, "评论过长"),
    TITLE_TOO_LONG(2005, "标题内容过长"),


    RESOURCE_REPORTED(2006, "该帖子被举报，正在审核中"),
    RESOURCE_HAVE_BEEN_CHECKED(200, "该帖子已审核通过"),
    RESOURCE_NORMAL(200, null),

    NOT_FOUND_ERROR(200404, HttpStatus.NOT_FOUND.getReasonPhrase()),
    SERVER_ERROR(200500, "系统错误, 请稍后重试"),
    ;

    private final Integer errorCode;
    private final String errorMsg;

}