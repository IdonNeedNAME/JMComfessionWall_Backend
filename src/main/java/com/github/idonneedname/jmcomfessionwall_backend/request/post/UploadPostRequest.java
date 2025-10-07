package com.github.idonneedname.jmcomfessionwall_backend.request.post;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class UploadPostRequest {
    public int user_id;
    public String title;
    public String content;
    public MultipartFile[] pictures;
    @JsonProperty("isPublic")
    public boolean isPublic;
    public boolean anonymity;
    public Boolean getIsPublic() {
        return isPublic;
    }
    public void setIsPublic(Boolean isPublic) {
        this.isPublic = isPublic;
    }
}
