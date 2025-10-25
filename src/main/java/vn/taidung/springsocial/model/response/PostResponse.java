package vn.taidung.springsocial.model.response;

import java.util.List;

public class PostResponse {
    private Long id;
    private Long userId;
    private String title;
    private String content;
    private List<String> tags;
    private List<CommentResponse> comments;

    public PostResponse() {
    }

    public PostResponse(Long id, Long userId, String title, String content, List<String> tags,
            List<CommentResponse> comments) {
        this.id = id;
        this.userId = userId;
        this.title = title;
        this.content = content;
        this.tags = tags;
        this.comments = comments;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public List<CommentResponse> getComments() {
        return comments;
    }

    public void setComments(List<CommentResponse> comments) {
        this.comments = comments;
    }

}
