package vn.taidung.springsocial.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import vn.taidung.springsocial.model.response.PostWithMetadata;
import vn.taidung.springsocial.repository.PostRepository;

@Service
public class FeedService {

    private final PostRepository postRepository;

    public FeedService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public List<PostWithMetadata> getUserFeedHandler(Long userId, Pageable pageable) {
        Page<PostWithMetadata> pageFeed = postRepository.getUserFeed(userId, pageable);
        return pageFeed.getContent();
    }
}
