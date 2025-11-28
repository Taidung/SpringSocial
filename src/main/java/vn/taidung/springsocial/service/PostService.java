package vn.taidung.springsocial.service;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import vn.taidung.springsocial.model.Post;
import vn.taidung.springsocial.model.request.CreatePostRequest;
import vn.taidung.springsocial.model.request.UpdatePostRequest;
import vn.taidung.springsocial.model.response.CommentResponse;
import vn.taidung.springsocial.model.response.PostResponse;
import vn.taidung.springsocial.repository.CommentRepository;
import vn.taidung.springsocial.repository.PostRepository;
import vn.taidung.springsocial.util.errors.NotFoundException;

@Service
public class PostService {
    private final PostRepository postRepository;
    private final ModelMapper modelMapper;
    private final CommentRepository commentRepository;

    public PostService(PostRepository postRepository, ModelMapper modelMapper, CommentRepository commentRepository) {
        this.postRepository = postRepository;
        this.modelMapper = modelMapper;
        this.commentRepository = commentRepository;
    }

    @Transactional
    public PostResponse createPostHandler(CreatePostRequest postRequest, Long userId) {
        Post post = this.modelMapper.map(postRequest, Post.class);
        post.setUserId(userId);
        this.postRepository.save(post);
        return this.modelMapper.map(post, PostResponse.class);
    }

    public PostResponse getPostHandler(Long id) throws NotFoundException {
        Optional<Post> optionalPost = this.postRepository.findById(id);
        if (!optionalPost.isPresent()) {
            throw new NotFoundException("post not found");
        }
        Post post = optionalPost.get();
        List<CommentResponse> comments = this.commentRepository.findAllByPostId(post.getId());
        PostResponse postResponse = modelMapper.map(post, PostResponse.class);
        postResponse.setComments(comments);

        return postResponse;
    }

    @Transactional
    public void deletePostHandler(Long id) throws NotFoundException {
        if (!this.postRepository.existsById(id)) {
            throw new NotFoundException("post not found");
        }

        this.postRepository.deleteById(id);
    }

    @Transactional
    public PostResponse updatePostHandler(Long id, UpdatePostRequest updatePostRequest) throws NotFoundException {
        Optional<Post> optionalPost = this.postRepository.findById(id);
        if (!optionalPost.isPresent()) {
            throw new NotFoundException("post not found");
        }
        Post post = optionalPost.get();

        if (updatePostRequest.getTitle() != null) {
            post.setTitle(updatePostRequest.getTitle());
        }

        if (updatePostRequest.getContent() != null) {
            post.setContent(updatePostRequest.getContent());
        }
        this.postRepository.save(post);
        return this.modelMapper.map(post, PostResponse.class);
    }

}
