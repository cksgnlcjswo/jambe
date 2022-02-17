package com.example.jambe.domain.Comment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment,Long> {

    @Query("SELECT c FROM Comment c INNER JOIN c.post p " +
            "WHERE p.id = :postId")
    List<Comment> findByPostId(@Param("postId") Long postId);
}
