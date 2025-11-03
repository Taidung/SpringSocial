package vn.taidung.springsocial.service;

import java.util.List;

import org.springframework.stereotype.Service;

import vn.taidung.springsocial.model.response.PostWithMetadata;
import vn.taidung.springsocial.repository.PostRepository;

@Service
public class FeedService {

    private final PostRepository postRepository;

    public FeedService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public List<PostWithMetadata> getUserFeedHandler(Long userId) {
        return postRepository.getUserFeed(userId);
    }
}
