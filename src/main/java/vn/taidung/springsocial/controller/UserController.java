package vn.taidung.springsocial.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import vn.taidung.springsocial.model.request.PostFeedFilter;
import vn.taidung.springsocial.model.response.PostWithMetadata;
import vn.taidung.springsocial.model.response.UserResponse;
import vn.taidung.springsocial.service.FeedService;
import vn.taidung.springsocial.service.FollowerService;
import vn.taidung.springsocial.service.UserService;
import vn.taidung.springsocial.util.annotation.ApiMessage;

@RestController
@RequestMapping("/v1")
@Validated
public class UserController {

    private final UserService userService;
    private final FollowerService followerService;
    private final FeedService feedService;

    public UserController(UserService userService, FollowerService followerService, FeedService feedService) {
        this.userService = userService;
        this.followerService = followerService;
        this.feedService = feedService;
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
            @PathVariable @Positive(message = "ID must be greater than zero") Long id,
            Authentication authentication) {
        Long currentUserId = userService.getUserByEmailHandler(authentication.getName()).getId();
        this.followerService.followUser(id, currentUserId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/users/{id}/unfollow")
    @ApiMessage("Unfollow a user")
    public ResponseEntity<Void> unfollowUser(
            @PathVariable @Positive(message = "ID must be greater than zero") Long id,
            Authentication authentication) {
        Long currentUserId = userService.getUserByEmailHandler(authentication.getName()).getId();
        this.followerService.unfollowUser(id, currentUserId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/users/feed")
    @ApiMessage("Get user feed")
    public ResponseEntity<List<PostWithMetadata>> getUserFeed(
            Authentication authentication,
            @Valid @ModelAttribute PostFeedFilter filter) {
        Long currentUserId = userService.getUserByEmailHandler(authentication.getName()).getId();
        Page<PostWithMetadata> feed = this.feedService.getUserFeedHandler(currentUserId, filter);
        return ResponseEntity.ok(feed.getContent());
    }
}
