package org.example.free_new_magazine.service;

import lombok.RequiredArgsConstructor;
import org.example.free_new_magazine.dto.FollowDTO;
import org.example.free_new_magazine.entity.Follow;
import org.example.free_new_magazine.entity.User;
import org.example.free_new_magazine.exception.ResourceAlreadyExistsException;
import org.example.free_new_magazine.exception.ResourceNotFoundException;
import org.example.free_new_magazine.mapper.FollowMapper;
import org.example.free_new_magazine.repository.FollowRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FollowService {

    private final FollowRepository followRepository;
    private final FollowMapper followMapper;
    private final CurrentUserService currentUserService;
    private final UserService userService;

    public List<Follow> getAllFollows() {
        return followRepository.findAll();
    }

    public FollowDTO createFollow(FollowDTO followDTO) {
        User follower = currentUserService.getCurrentUser();
        User following = userService.getUserEntityById(followDTO.getFollowingId());

        if(followRepository.existsByFollowerIdAndFollowingId(follower.getId(), following.getId())){
            throw new ResourceAlreadyExistsException("This follow already exists.");
        }
        Follow f = new Follow();
        f.setFollower(follower);
        f.setFollowing(following);
        Follow saved = followRepository.save(f);
        return followMapper.toDTO(saved );
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
