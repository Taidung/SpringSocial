package vn.taidung.springsocial.model;

import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

@Entity
@Table(name = "followers")
public class Follower {

    @EmbeddedId
    private FollowerId id;

    @Column(name = "created_at")
    private Instant createdAt;

    public Follower() {
    }

    public Follower(FollowerId id) {
        this.id = id;
    }

    public FollowerId getId() {
        return id;
    }

    public void setId(FollowerId id) {
        this.id = id;
    }

    @PrePersist
    public void handleBeforeCreate() {
        this.createdAt = Instant.now();
    }
}
