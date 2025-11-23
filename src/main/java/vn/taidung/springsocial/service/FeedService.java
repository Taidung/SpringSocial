package vn.taidung.springsocial.service;

import java.sql.SQLException;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import vn.taidung.springsocial.model.request.PostFeedFilter;
import vn.taidung.springsocial.model.response.PostWithMetadata;
import vn.taidung.springsocial.repository.PostRepository;

@Service
public class FeedService {

    private static final Logger LOGGER = LoggerFactory.getLogger(FeedService.class);

    private final PostRepository postRepository;

    public FeedService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public Page<PostWithMetadata> getUserFeedHandler(Long userId, PostFeedFilter filter) {
        Pageable pageable = PageRequest.of(
                filter.getPageNumber(),
                filter.getPageSize(),
                filter.getSort().equalsIgnoreCase("asc")
                        ? Sort.by("created_at").ascending()
                        : Sort.by("created_at").descending());

        String search = filter.hasSearch() ? filter.getSearch() : null;
        String tagsParam = filter.hasTags()
                ? "{" + String.join(",", filter.getTags()) + "}"
                : null;

        Page<Object[]> results = postRepository.getUserFeed(
                userId,
                search,
                tagsParam,
                pageable);

        return results.map(this::mapToPostWithMetadata);
    }

    private PostWithMetadata mapToPostWithMetadata(Object[] row) {
        Long id = ((Number) row[0]).longValue();
        String title = (String) row[1];
        String content = (String) row[2];

        Instant createdAt = null;
        if (row[3] instanceof java.sql.Timestamp) {
            createdAt = ((java.sql.Timestamp) row[3]).toInstant();
        }

        List<String> tags = convertPostgresArrayToList(row[4]);

        Long userId = ((Number) row[5]).longValue();
        String username = (String) row[6];
        Long commentsCount = ((Number) row[7]).longValue();

        return new PostWithMetadata(id, title, content, createdAt, tags,
                userId, username, commentsCount);
    }

    private List<String> convertPostgresArrayToList(Object arrayObj) {
        if (arrayObj == null) {
            return List.of();
        }

        try {
            if (arrayObj instanceof String[]) {
                return Arrays.asList((String[]) arrayObj);
            } else if (arrayObj instanceof java.sql.Array) {
                java.sql.Array sqlArray = (java.sql.Array) arrayObj;
                Object arr = sqlArray.getArray();
                if (arr instanceof String[]) {
                    return Arrays.asList((String[]) arr);
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error converting PostgreSQL array: {}", e.getMessage());
        }

        return List.of();
    }
}
