package vn.taidung.springsocial.model;

import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "user_invitations")
public class UserInvitation {

    @Id
    @Column(name = "token", nullable = false)
    private byte[] token;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "expiry", nullable = false)
    private Instant expiry;

    public byte[] getToken() {
        return token;
    }

    public void setToken(byte[] token) {
        this.token = token;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Instant getExpiry() {
        return expiry;
    }

    public void setExpiry(Instant expiry) {
        this.expiry = expiry;
    }
}
