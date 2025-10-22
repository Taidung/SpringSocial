package vn.taidung.springsocial.controller;

import java.util.Optional;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import vn.taidung.springsocial.model.Post;
import vn.taidung.springsocial.model.request.CreatePostRequest;
import vn.taidung.springsocial.model.request.UpdatePostRequest;
import vn.taidung.springsocial.service.PostService;

@RestController
@RequestMapping("/v1")
public class PostController {
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping("/posts")
    public Post createPost(@RequestBody CreatePostRequest postRequest) {
        return this.postService.createPostHandler(postRequest);
    }

    @GetMapping("/posts/{id}")
    public Optional<Post> getPost(@PathVariable Long id) {
        return this.postService.getPostHandler(id);
    }

    @DeleteMapping("/posts/{id}")
    public void deletePost(@PathVariable Long id) {
        this.postService.deletePostHandler(id);
    }

    @PatchMapping("/posts/{id}")
    public Post updatePost(@PathVariable Long id, @RequestBody UpdatePostRequest postRequest) {
        return this.postService.updatePostHandler(id, postRequest);
    }
}
