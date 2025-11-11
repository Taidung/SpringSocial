package vn.taidung.springsocial.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import vn.taidung.springsocial.model.Post;
import vn.taidung.springsocial.model.response.PostWithMetadata;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    @Query("""
            SELECT new vn.taidung.springsocial.model.response.PostWithMetadata(
                p.id,
                p.title,
                p.content,
                p.tags,
                u.id,
                u.username,
                COUNT(c.id)
            )
            FROM Post p
            LEFT JOIN User u ON u.id = p.userId
            LEFT JOIN Comment c ON c.postId = p.id
            LEFT JOIN Follower f ON f.id.followerId = p.userId OR p.userId = :userId
            WHERE f.id.userId = :userId OR p.userId = :userId
            GROUP BY p.id, u.id, u.username
            ORDER BY p.createdAt DESC
            """)
    Page<PostWithMetadata> getUserFeed(@Param("userId") Long userId, Pageable pageable);
}