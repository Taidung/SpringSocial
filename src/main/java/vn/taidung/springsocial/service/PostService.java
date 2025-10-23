package vn.taidung.springsocial.service;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import vn.taidung.springsocial.model.Post;
import vn.taidung.springsocial.model.request.CreatePostRequest;
import vn.taidung.springsocial.model.request.UpdatePostRequest;
import vn.taidung.springsocial.repository.PostRepository;
import vn.taidung.springsocial.util.errors.PostNotFoundException;

@Service
public class PostService {
    private final PostRepository postRepository;
    private final ModelMapper modelMapper;

    public PostService(PostRepository postRepository, ModelMapper modelMapper) {
        this.postRepository = postRepository;
        this.modelMapper = modelMapper;
    }

    @Transactional
    public Post createPostHandler(CreatePostRequest postRequest) {
        Post post = this.modelMapper.map(postRequest, Post.class);
        return this.postRepository.save(post);
    }

    public Post getPostHandler(Long id) {
        Optional<Post> optionalPost = this.postRepository.findById(id);
        if (optionalPost.isPresent()) {
            return optionalPost.get();
        }
        return null;
    }

    public void deletePostHandler(Long id) throws PostNotFoundException {
        if (!this.postRepository.existsById(id)) {
            throw new PostNotFoundException("post not found");
        }

        this.postRepository.deleteById(id);
    }

    public Post updatePostHandler(Long id, UpdatePostRequest updatePostRequest) throws PostNotFoundException {
        Optional<Post> optionalPost = this.postRepository.findById(id);
        if (optionalPost.isPresent()) {
            Post post = this.modelMapper.map(optionalPost.get(), Post.class);

            if (updatePostRequest.getTitle() != null) {
                post.setTitle(updatePostRequest.getTitle());
            }

            if (updatePostRequest.getContent() != null) {
                post.setContent(updatePostRequest.getContent());
            }
            return this.postRepository.save(post);
        } else {
            throw new PostNotFoundException("Post not found");
        }
    }

}
