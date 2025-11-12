package vn.taidung.springsocial.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import vn.taidung.springsocial.model.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    @Query(value = """
            SELECT
                p.id,
                p.title,
                p.content,
                p.created_at,
                p.tags,
                p.user_id,
                u.username,
                COUNT(c.id) AS comments_count
            FROM posts p
            LEFT JOIN users u ON p.user_id = u.id
            LEFT JOIN comments c ON p.id = c.post_id
            LEFT JOIN followers f ON f.user_id = p.user_id AND f.follower_id = :userId
            WHERE
                (p.user_id = :userId OR f.user_id IS NOT NULL) AND
                (:search IS NULL
                 OR p.title ILIKE CONCAT('%', :search, '%')
                 OR p.content ILIKE CONCAT('%', :search, '%')) AND
                (:tags IS NULL OR p.tags @> CAST(:tags AS varchar[]))
            GROUP BY p.id, u.username
            """, nativeQuery = true)
    Page<Object[]> getUserFeed(
            @Param("userId") Long userId,
            @Param("search") String search,
            @Param("tags") String tags,
            Pageable pageable);
}