package vn.taidung.springsocial.service;

import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import vn.taidung.springsocial.model.Follower;
import vn.taidung.springsocial.model.FollowerId;
import vn.taidung.springsocial.model.User;
import vn.taidung.springsocial.repository.FollowerRepository;
import vn.taidung.springsocial.repository.UserRepository;
import vn.taidung.springsocial.util.errors.ConflictException;
import vn.taidung.springsocial.util.errors.NotFoundException;

@Service
public class FollowerService {
    private final FollowerRepository followerRepository;
    private final UserRepository userRepository;

    public FollowerService(FollowerRepository followerRepository, UserRepository userRepository) {
        this.followerRepository = followerRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public void followUser(Long targetUserId, Long currentUserId) {
        User target = userRepository.findByIdAndIsActiveTrue(targetUserId)
                .orElseThrow(() -> new NotFoundException("user not found"));

        User current = userRepository.findByIdAndIsActiveTrue(currentUserId)
                .orElseThrow(() -> new NotFoundException("user not found"));

        if (target.getId() == current.getId()) {
            throw new ConflictException("can't following yourself");
        }

        FollowerId id = new FollowerId(target.getId(), current.getId());

        if (followerRepository.existsById(id)) {
            throw new ConflictException("already following this user");
        }

        followerRepository.save(new Follower(id));
    }

    public void unfollowUser(Long targetUserId, Long currentUserId) {
        User target = userRepository.findByIdAndIsActiveTrue(targetUserId)
                .orElseThrow(() -> new NotFoundException("user not found"));

        User current = userRepository.findByIdAndIsActiveTrue(currentUserId)
                .orElseThrow(() -> new NotFoundException("user not found"));

        if (target.getId() == current.getId()) {
            throw new ConflictException("can't unfollowing yourself");
        }

        FollowerId id = new FollowerId(target.getId(), current.getId());

        followerRepository.deleteById(id);
    }
}
