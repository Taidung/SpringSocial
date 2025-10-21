package vn.taidung.springsocial.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import vn.taidung.springsocial.model.Post;
import vn.taidung.springsocial.model.User;
import vn.taidung.springsocial.model.request.CreatePostRequest;
import vn.taidung.springsocial.repository.PostRepository;

@Service
public class PostService {
    private final PostRepository postRepository;
    private final ModelMapper modelMapper;

    public PostService(PostRepository postRepository, ModelMapper modelMapper) {
        this.postRepository = postRepository;
        this.modelMapper = modelMapper;
    }

    public Post createPostHandler(CreatePostRequest postRequest) {
        Post post = modelMapper.map(postRequest, Post.class);
        User user = new User();
        user.setId(2L);
        post.setUser(user);
        return postRepository.save(post);
    }
}
