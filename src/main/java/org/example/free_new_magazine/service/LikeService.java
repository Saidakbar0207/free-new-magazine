package org.example.free_new_magazine.service;

import lombok.RequiredArgsConstructor;
import org.example.free_new_magazine.entity.Like;
import org.example.free_new_magazine.entity.Post;
import org.example.free_new_magazine.entity.User;
import org.example.free_new_magazine.exception.ResourceAlreadyExistsException;
import org.example.free_new_magazine.exception.ResourceNotFoundException;
import org.example.free_new_magazine.repository.LikeRepository;
import org.example.free_new_magazine.repository.PostRepository;
import org.example.free_new_magazine.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LikeService {

    private final LikeRepository likeRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;


    public Like likePost(Long userId, Long postId) {


        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));


        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found"));


        likeRepository.findByUserIdAndPostId(userId, postId).ifPresent(l -> {
            throw new ResourceAlreadyExistsException("You already liked this post");
        });

        Like like = Like.builder()
                .user(user)
                .post(post)
                .build();

        return likeRepository.save(like);
    }


        public void unlikePost(Long userId, Long postId) {
        Like like = likeRepository.findByUserIdAndPostId(userId, postId)
                .orElseThrow(() -> new ResourceNotFoundException("Like not found"));

        likeRepository.delete(like);
    }


    public Long countLikesByPost(Long postId) {
        return likeRepository.countByPostId(postId);
    }

    public List<Like> getLikesByUser(Long userId) {
        return likeRepository.findByUserId(userId);
    }
}
