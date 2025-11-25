package org.example.free_new_magazine.service;

import lombok.RequiredArgsConstructor;
import org.example.free_new_magazine.entity.Follow;
import org.example.free_new_magazine.exception.ResourceAlreadyExistsException;
import org.example.free_new_magazine.exception.ResourceNotFoundException;
import org.example.free_new_magazine.repository.FollowRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FollowService {

    private final FollowRepository followRepository;

    public List<Follow> getAllFollows() {
        return followRepository.findAll();
    }

    public Follow createFollow(Follow follow) {
        if(followRepository.existsByFollowerIdAndFollowingId(follow.getFollower().getId(), follow.getFollowing().getId())){
            throw new ResourceAlreadyExistsException("This follow already exists.");
        }
        return followRepository.save(follow);
    }

    public void deleteFollow(Long id) {
        Follow follow = followRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Follow not found with id: " + id));
        followRepository.delete(follow);
    }

    public List<Follow> getFollowers(Long userId) {
        return followRepository.findByFollowingId(userId);
    }

    public List<Follow> getFollowing(Long userId) {
        return followRepository.findByFollowerId(userId);
    }
}
