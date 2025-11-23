package vn.taidung.springsocial.repository;

import java.time.Instant;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import vn.taidung.springsocial.model.UserInvitation;

public interface UserInvitationRepository extends JpaRepository<UserInvitation, byte[]> {
    Optional<UserInvitation> findByTokenAndExpiryAfter(byte[] token, Instant now);

    void deleteByUserId(Long userId);
}
