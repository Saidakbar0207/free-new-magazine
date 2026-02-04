package org.example.free_new_magazine.repository;

import org.example.free_new_magazine.entity.SavedPost;
import org.example.free_new_magazine.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SavedPostRepository extends JpaRepository<SavedPost,Long> {
    List<SavedPost> findByUser_IdOrderBySavedAtDesc(Long userId);
    boolean existsByUser_IdAndPost_Id(Long userId, Long postId);
    void deleteByUser_IdAndPost_Id(Long userId, Long postId);

    @Query("select  sp.post.id from SavedPost sp where sp.user.id =:userId")
    List<Long> findPostIdsByUserId(@Param("userId") Long userId);
}
