package vn.taidung.springsocial.service;

import java.util.Optional;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import vn.taidung.springsocial.model.Post;
import vn.taidung.springsocial.repository.PostRepository;

@Component("postPermission")
public class PostPermission {
    private static final long LEVEL_MODERATOR = 2L;
    private static final long LEVEL_ADMIN = 3L;

    private final PostRepository postRepository;

    public PostPermission(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public boolean canUpdate(Authentication auth, Long postId) {
        return canModify(auth, postId, LEVEL_MODERATOR);
    }

    public boolean canDelete(Authentication auth, Long postId) {
        return canModify(auth, postId, LEVEL_ADMIN);
    }

    private boolean canModify(Authentication auth, Long postId, long requiredLevel) {
        if (auth == null || !auth.isAuthenticated())
            return false;
        Object principal = auth.getPrincipal();
        if (!(principal instanceof CustomUser))
            return false;

        CustomUser user = (CustomUser) principal;
        Optional<Post> postOpt = postRepository.findById(postId);
        if (!postOpt.isPresent())
            return false;

        Post post = postOpt.get();
        if (post.getUserId() != null && post.getUserId().equals(user.getId()))
            return true;

        Long level = user.getRoleLevel();
        return level != null && level >= requiredLevel;
    }
}
