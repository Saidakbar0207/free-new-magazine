package org.example.free_new_magazine.repository;

import org.example.free_new_magazine.entity.Category;
import org.example.free_new_magazine.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment,Long> {

    List<Comment> findByPostId(Long postId);

}

