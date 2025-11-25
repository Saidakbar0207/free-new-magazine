package org.example.free_new_magazine.repository;

import org.example.free_new_magazine.entity.SavedPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SavedPostRepository extends JpaRepository<SavedPost,Long> {
    List<SavedPost> findByUserId(Long userId);

}
