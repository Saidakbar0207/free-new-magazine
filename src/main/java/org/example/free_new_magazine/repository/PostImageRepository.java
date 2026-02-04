package org.example.free_new_magazine.repository;

import org.example.free_new_magazine.entity.PostImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostImageRepository    extends JpaRepository<PostImage,Long> {
    List<PostImage> findByPostId(Long postId);

}
