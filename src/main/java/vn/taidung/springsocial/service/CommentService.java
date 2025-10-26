package vn.taidung.springsocial.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import vn.taidung.springsocial.model.Comment;
import vn.taidung.springsocial.model.User;
import vn.taidung.springsocial.model.request.CreateCommentRequest;
import vn.taidung.springsocial.model.response.CommentResponse;
import vn.taidung.springsocial.model.response.UserResponse;
import vn.taidung.springsocial.repository.CommentRepository;
import vn.taidung.springsocial.repository.PostRepository;
import vn.taidung.springsocial.repository.UserRepository;
import vn.taidung.springsocial.util.errors.PostNotFoundException;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final ModelMapper modelMapper;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public CommentService(CommentRepository commentRepository, ModelMapper modelMapper, PostRepository postRepository,
            UserRepository userRepository) {
        this.commentRepository = commentRepository;
        this.modelMapper = modelMapper;
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public CommentResponse createCommentHandler(CreateCommentRequest commentRequest, Long postId) {
        if (!this.postRepository.existsById(postId)) {
            throw new PostNotFoundException("post not found");
        }

        Comment comment = new Comment();
        comment.setContent(commentRequest.getContent());
        comment.setPostId(postId);
        comment.setUserId(commentRequest.getUserId());
        this.commentRepository.save(comment);

        User user = this.userRepository.findById(commentRequest.getUserId()).get();
        CommentResponse response = this.modelMapper.map(comment, CommentResponse.class);
        response.setUser(new UserResponse(user.getId(), user.getUsername(), user.getEmail()));
        return response;
    }
}
