package vn.taidung.springsocial.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import vn.taidung.springsocial.model.Comment;
import vn.taidung.springsocial.model.request.CreateCommentRequest;
import vn.taidung.springsocial.model.request.CreatePostRequest;
import vn.taidung.springsocial.model.request.UpdatePostRequest;
import vn.taidung.springsocial.model.response.CommentResponse;
import vn.taidung.springsocial.model.response.PostResponse;
import vn.taidung.springsocial.service.CommentService;
import vn.taidung.springsocial.service.PostService;
import vn.taidung.springsocial.util.annotation.ApiMessage;

@RestController
@RequestMapping("/v1")
@Validated
public class PostController {
    private final PostService postService;
    private final CommentService commentService;

    public PostController(PostService postService, CommentService commentService) {
        this.postService = postService;
        this.commentService = commentService;
    }

    @PostMapping("/posts")
    @ApiMessage("Create a post")
    public ResponseEntity<PostResponse> createPost(@RequestBody @Valid CreatePostRequest postRequest) {
        PostResponse post = this.postService.createPostHandler(postRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(post);
    }

    @GetMapping("/posts/{id}")
    @ApiMessage("Get a post by ID")
    public ResponseEntity<PostResponse> getPost(
            @PathVariable @Positive(message = "Post ID must be greater than zero") Long id) {
        PostResponse postResponse = this.postService.getPostHandler(id);
        return ResponseEntity.ok(postResponse);
    }

    @DeleteMapping("/posts/{id}")
    @ApiMessage("Delete a post")
    public ResponseEntity<Void> deletePost(
            @PathVariable @Positive(message = "Post ID must be greater than zero") Long id) {
        this.postService.deletePostHandler(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/posts/{id}")
    @ApiMessage("Update a post")
    public ResponseEntity<PostResponse> updatePost(
            @PathVariable @Positive(message = "Post ID must be greater than zero") Long id,
            @RequestBody UpdatePostRequest postRequest) {
        PostResponse post = this.postService.updatePostHandler(id, postRequest);
        return ResponseEntity.ok(post);
    }

    @PostMapping("/posts/{id}/comments")
    @ApiMessage("Add a comment to a post")
    public ResponseEntity<CommentResponse> createComment(
            @PathVariable @Positive(message = "Post ID must be greater than zero") Long id,
            @RequestBody @Valid CreateCommentRequest commentRequest) {
        CommentResponse commentResponse = this.commentService.createCommentHandler(commentRequest, id);
        return ResponseEntity.status(HttpStatus.CREATED).body(commentResponse);
    }
}
