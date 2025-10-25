package vn.taidung.springsocial.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import vn.taidung.springsocial.model.Comment;
import vn.taidung.springsocial.model.response.CommentResponse;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query("""
            SELECT new vn.taidung.springsocial.model.response.CommentResponse(
                c.id, c.postId, c.userId, c.content,
                u.id, u.username, u.email
            )
            FROM Comment c
            JOIN User u ON c.userId = u.id
            WHERE c.postId = :postId
            ORDER BY c.createdAt DESC
            """)
    List<CommentResponse> findAllByPostId(@Param("postId") Long postId);
}
