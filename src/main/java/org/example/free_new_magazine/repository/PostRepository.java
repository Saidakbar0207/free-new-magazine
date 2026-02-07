package org.example.free_new_magazine.repository;


import org.example.free_new_magazine.entity.Post;
import org.example.free_new_magazine.entity.PostStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post,Long> {

    List<Post> findTopByOrderByCreateAtDesc(Pageable pageable);

    List<Post> findByAuthor_Id(Long id);

    Page<Post> findByStatusAndIsDeletedFalse(PostStatus postStatus, Pageable pageable);
    Optional<Post>  findByIdAndStatusAndIsDeletedFalse(Long id, PostStatus postStatus);
}
