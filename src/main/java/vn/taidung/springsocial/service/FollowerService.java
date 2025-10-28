package vn.taidung.springsocial.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import vn.taidung.springsocial.model.Follower;
import vn.taidung.springsocial.model.FollowerId;
import vn.taidung.springsocial.model.User;
import vn.taidung.springsocial.model.request.FollowUserRequest;
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
    public void followUser(Long followerId, FollowUserRequest followUserRequest)
            throws NotFoundException, ConflictException {
        Optional<User> optionalUser = this.userRepository.findById(followerId);
        if (!optionalUser.isPresent()) {
            throw new NotFoundException("user not found");
        }

        FollowerId id = new FollowerId(followUserRequest.getUserId(), followerId);

        if (followerRepository.existsById(id)) {
            throw new ConflictException("already following this user");
        }

        followerRepository.save(new Follower(id));
    }

    public void unfollowUser(Long followerId, FollowUserRequest followUserRequest)
            throws NotFoundException {
        Optional<User> optionalUser = this.userRepository.findById(followerId);
        if (!optionalUser.isPresent()) {
            throw new NotFoundException("user not found");
        }

        FollowerId id = new FollowerId(followUserRequest.getUserId(), followerId);

        followerRepository.deleteById(id);
        ;
    }
}
