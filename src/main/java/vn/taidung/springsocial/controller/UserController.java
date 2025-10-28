package vn.taidung.springsocial.controller;

import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.constraints.Positive;
import vn.taidung.springsocial.model.request.FollowUserRequest;
import vn.taidung.springsocial.model.response.UserResponse;
import vn.taidung.springsocial.service.FollowerService;
import vn.taidung.springsocial.service.UserService;
import vn.taidung.springsocial.util.annotation.ApiMessage;

@RestController
@RequestMapping("/v1")
@Validated
public class UserController {

    private final UserService userService;
    private final FollowerService followerService;

    public UserController(UserService userService, FollowerService followerService) {
        this.userService = userService;
        this.followerService = followerService;
    }

    @GetMapping("/users/{id}")
    @ApiMessage("Get a user by ID")
    public ResponseEntity<UserResponse> getUser(
            @PathVariable @Positive(message = "User ID must be greater than zero") Long id) {
        UserResponse userResponse = this.userService.getUserHandler(id);
        return ResponseEntity.ok(userResponse);
    }

    @PutMapping("/users/{id}/follow")
    @ApiMessage("Follow a user")
    public ResponseEntity<Void> followUser(
            @RequestBody FollowUserRequest followUserRequest,
            @PathVariable @Positive(message = "ID must be greater than zero") Long id) {
        this.followerService.followUser(id, followUserRequest);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/users/{id}/unfollow")
    @ApiMessage("Unfollow a user")
    public ResponseEntity<Void> unfollowUser(
            @RequestBody FollowUserRequest followUserRequest,
            @PathVariable @Positive(message = "ID must be greater than zero") Long id) {
        this.followerService.unfollowUser(id, followUserRequest);
        return ResponseEntity.noContent().build();
    }
}
