package com.example.jambe.domain.Post;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PostRepository extends JpaRepository<Post,Long> {
    @Query("SELECT p FROM Post p INNER JOIN p.board b " +
            "WHERE b.id = :id")
    Page<Post> findAllByBoard(@Param("id") Long id, Pageable pageable);
}
