package org.example.free_new_magazine.repository;

import org.example.free_new_magazine.entity.Follow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FollowRepository  extends JpaRepository<Follow,Long> {

    List<Follow> findByFollowerId(Long userId);
    List<Follow> findByFollowingId(Long userId);

    boolean existsByFollowerIdAndFollowingId(Long followerId, Long followingId);

   Optional<Follow> findByFollowerIdAndFollowingId(Long followerId, Long followingId);
}
