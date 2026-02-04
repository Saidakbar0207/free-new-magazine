package org.example.free_new_magazine.repository;

import org.example.free_new_magazine.entity.PostVideo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostVideoRepository extends JpaRepository<PostVideo, Long> {
    List<PostVideo> findAllByPostId(Long postId);

}
