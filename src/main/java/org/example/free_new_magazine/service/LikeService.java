package org.example.free_new_magazine.service;

import lombok.RequiredArgsConstructor;
import org.example.free_new_magazine.dto.LikeDTO;
import org.example.free_new_magazine.entity.Like;
import org.example.free_new_magazine.entity.Post;
import org.example.free_new_magazine.entity.User;
import org.example.free_new_magazine.exception.ConflictException;
import org.example.free_new_magazine.exception.NotFoundException;
import org.example.free_new_magazine.mapper.LikeMapper;
import org.example.free_new_magazine.repository.LikeRepository;
import org.example.free_new_magazine.repository.PostRepository;
import org.example.free_new_magazine.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LikeService {

    private final LikeRepository likeRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final CurrentUserService currentUserService;
    private final LikeMapper likeMapper;


    public LikeDTO likePost(Long postId) {

        Long userId = currentUserService.getCurrentUser().getId();

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found"));

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new NotFoundException("Post not found"));


        likeRepository.findByUserIdAndPostId(userId, postId).ifPresent(l -> {
            throw new ConflictException("You already liked this post");
        });

        Like like = Like.builder()
                .user(user)
                .post(post)
                .build();

        Like save = likeRepository.save(like);
        return likeMapper.toDTO(save);
    }


    @Transactional
        public void unlikePost(Long postId) {
        User user = currentUserService.getCurrentUser();
        Like like = likeRepository.findByUserIdAndPostId(user.getId(), postId)
                .orElseThrow(() -> new NotFoundException("Like not found"));

        likeRepository.delete(like);
    }


    public Long countLikesByPost(Long postId) {
        return likeRepository.countByPostId(postId);
    }

    public List<LikeDTO> getLikesByUser() {
        User user = currentUserService.getCurrentUser();
        return   likeRepository.findByUserId(user.getId())
                .stream()
                .map(likeMapper::toDTO)
                .toList();
    }
}
