package vn.taidung.springsocial.service;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import vn.taidung.springsocial.model.Post;
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
        return this.postRepository.save(post);
    }

    public Optional<Post> getPostHandler(Long id) {
        return postRepository.findById(id);
    }

}
