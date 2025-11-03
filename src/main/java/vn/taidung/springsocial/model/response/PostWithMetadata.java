package vn.taidung.springsocial.model.response;

import java.util.List;

public class PostWithMetadata {
    private Long id;
    private String title;
    private String content;
    private List<String> tags;
    private UserResponse user;
    private Long commentsCount;

    public PostWithMetadata() {
    }

    public PostWithMetadata(Long id, String title, String content, List<String> tags,
            Long userId, String username, Long commentsCount) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.tags = tags;
        this.user = new UserResponse(userId, username);
        this.commentsCount = commentsCount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public UserResponse getUser() {
        return user;
    }

    public void setUser(UserResponse user) {
        this.user = user;
    }

    public Long getCommentsCount() {
        return commentsCount;
    }

    public void setCommentsCount(Long commentsCount) {
        this.commentsCount = commentsCount;
    }

}
