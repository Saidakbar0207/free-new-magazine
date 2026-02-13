package org.example.free_new_magazine.service;

import lombok.RequiredArgsConstructor;
import org.example.free_new_magazine.dto.FollowDTO;
import org.example.free_new_magazine.entity.Follow;
import org.example.free_new_magazine.entity.Role;
import org.example.free_new_magazine.entity.User;
import org.example.free_new_magazine.exception.ConflictException;
import org.example.free_new_magazine.exception.ForbiddenException;
import org.example.free_new_magazine.exception.NotFoundException;
import org.example.free_new_magazine.mapper.FollowMapper;
import org.example.free_new_magazine.repository.FollowRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FollowService {

    private final FollowRepository followRepository;
    private final FollowMapper followMapper;
    private final CurrentUserService currentUserService;
    private final UserService userService;

    public List<FollowDTO> getAllFollows() {
        return followRepository.findAll()
                .stream()
                .map(followMapper::toDTO)
                .toList();
    }

    public FollowDTO follow(Long followingId) {
        User follower = currentUserService.getCurrentUser();
        User following = userService.getUserEntityById(followingId);

        if(follower.getId().equals(following.getId())) {
            throw new ConflictException("You can't follow yourself");
        }

        if(followRepository.existsByFollowerIdAndFollowingId(follower.getId(), following.getId())) {
            throw new ConflictException("You are already following this user");
        }
        Follow follow = Follow.builder()
                .follower(follower)
                .following(following)
                .build();
        return followMapper.toDTO(followRepository.save(follow));
    }

    @Transactional
    public void unfollow(Long followingId) {
        User follower = currentUserService.getCurrentUser();
        Follow follow = followRepository.findByFollowerIdAndFollowingId(follower.getId(), followingId)
                .orElseThrow(() -> new NotFoundException("Follow not found"));
        followRepository.delete(follow);
    }

    public List<FollowDTO> getMyFollowers() {
        Long me = currentUserService.getCurrentUser().getId();
        return followRepository.findByFollowingId(me)
                .stream()
                .map(followMapper::toDTO)
                .toList();
    }

    public List<FollowDTO> getMyFollowing(){
        Long me = currentUserService.getCurrentUser().getId();
        return followRepository.findByFollowerId(me)
                .stream()
                .map(followMapper::toDTO)
                .toList();
    }


}
