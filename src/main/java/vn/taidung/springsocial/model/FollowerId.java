package vn.taidung.springsocial.model;

import java.io.Serializable;

import jakarta.persistence.Embeddable;

@Embeddable
public class FollowerId implements Serializable {

    private Long userId;
    private Long followerId;

    public FollowerId() {
    }

    public FollowerId(Long userId, Long followerId) {
        this.userId = userId;
        this.followerId = followerId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getFollowerId() {
        return followerId;
    }

    public void setFollowerId(Long followerId) {
        this.followerId = followerId;
    }

}
