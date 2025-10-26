package vn.taidung.springsocial.model.request;

import jakarta.validation.constraints.NotBlank;

public class CreateCommentRequest {
    private Long userId;

    @NotBlank(message = "Content must not be blank")
    private String content;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
