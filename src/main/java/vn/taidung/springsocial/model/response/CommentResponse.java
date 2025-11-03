package vn.taidung.springsocial.model.response;

public class CommentResponse {
    private Long id;
    private Long postId;
    private Long userId;
    private String content;
    private UserResponse user;

    public CommentResponse() {
    }

    public CommentResponse(Long id, Long postId, Long userId, String content, Long uId,
            String username) {
        this.id = id;
        this.postId = postId;
        this.userId = userId;
        this.content = content;
        this.user = new UserResponse(userId, username);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

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

    public UserResponse getUser() {
        return user;
    }

    public void setUser(UserResponse user) {
        this.user = user;
    }

}
