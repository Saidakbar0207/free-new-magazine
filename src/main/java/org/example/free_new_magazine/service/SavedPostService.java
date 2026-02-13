package org.example.free_new_magazine.service;

import lombok.RequiredArgsConstructor;
import org.example.free_new_magazine.dto.PostResponseDTO;
import org.example.free_new_magazine.dto.SavedPostDTO;
import org.example.free_new_magazine.entity.Post;
import org.example.free_new_magazine.entity.SavedPost;
import org.example.free_new_magazine.entity.User;
import org.example.free_new_magazine.exception.NotFoundException;
import org.example.free_new_magazine.mapper.PostMapper;
import org.example.free_new_magazine.mapper.SavedPostMapper;
import org.example.free_new_magazine.repository.PostRepository;
import org.example.free_new_magazine.repository.SavedPostRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SavedPostService {

    private final SavedPostRepository savedPostRepository;
    private final PostRepository postRepository;
    private final PostService postService;
    private final CurrentUserService currentUserService;
    private final PostMapper postMapper;


    public List<Long> getMySavedPostIds() {
        User user = currentUserService.getCurrentUser();
        return savedPostRepository.findPostIdsByUserId(user.getId());
    }

    public List<PostResponseDTO> getMySavedPosts() {
        User user = currentUserService.getCurrentUser();
        return savedPostRepository.findByUser_IdOrderBySavedAtDesc(user.getId())
                .stream()
                .map(SavedPost::getPost)
                .map(postMapper::toResponseDTO)
                .toList();

    }

    public void save(Long postId) {
        User user = currentUserService.getCurrentUser();
        if(savedPostRepository.existsByUser_IdAndPost_Id(user.getId(), postId)) return;

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new NotFoundException("Post not found" + postId) );
        SavedPost savedPost = SavedPost.builder()
                .user(user)
                .post(post)
                .build();
        savedPostRepository.save(savedPost);
    }

    @Transactional
    public void unsave(Long postId) {
        User user = currentUserService.getCurrentUser();
        savedPostRepository.deleteByUser_IdAndPost_Id(user.getId(), postId);
    }
}
