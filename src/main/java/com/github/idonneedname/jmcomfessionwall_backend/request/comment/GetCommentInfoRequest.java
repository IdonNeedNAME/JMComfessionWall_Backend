package com.github.idonneedname.jmcomfessionwall_backend.request.comment;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetCommentInfoRequest {
    public int user_id;
    public int comment_id;
}
