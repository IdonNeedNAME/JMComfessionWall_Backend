package com.github.idonneedname.jmcomfessionwall_backend.request.comment;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UploadCommentRequest {
      public int user_id;
      public int type;
      public String content;
      public int target_id;
}
