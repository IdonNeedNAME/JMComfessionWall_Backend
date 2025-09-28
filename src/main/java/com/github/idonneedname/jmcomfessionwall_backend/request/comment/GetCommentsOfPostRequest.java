package com.github.idonneedname.jmcomfessionwall_backend.request.comment;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetCommentsOfPostRequest {
    public int user_id;
    public int post_id;
}
