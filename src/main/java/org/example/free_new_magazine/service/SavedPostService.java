package org.example.free_new_magazine.service;

import org.example.free_new_magazine.entity.SavedPost;
import org.example.free_new_magazine.repository.SavedPostRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SavedPostService {

    private final SavedPostRepository savedPostRepository;

    public SavedPostService(SavedPostRepository savedPostRepository) {
        this.savedPostRepository = savedPostRepository;
    }

    public List<SavedPost> getAllSavedPosts() {
        return savedPostRepository.findAll();
    }

    public SavedPost savePost(SavedPost savedPost) {
        return savedPostRepository.save(savedPost);
    }


    public List<SavedPost> getSavedPostsByUser(Long userId) {
        return savedPostRepository.findByUserId(userId);
    }

    public void deleteSavedPost(Long id) {
        savedPostRepository.deleteById(id);
    }
}
