package org.example.free_new_magazine.repository;

import org.example.free_new_magazine.entity.PostTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostTagRepository  extends JpaRepository<PostTag,Long> {

    List<PostTag> findByPostId(Long postId);
}
