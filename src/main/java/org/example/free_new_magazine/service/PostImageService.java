package org.example.free_new_magazine.service;

import org.example.free_new_magazine.entity.PostImage;
import org.example.free_new_magazine.exception.NotFoundException;
import org.example.free_new_magazine.repository.PostImageRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostImageService {

    private final PostImageRepository postImageRepository;

    public PostImageService(PostImageRepository postImageRepository) {
        this.postImageRepository = postImageRepository;
    }

    public List<PostImage> getAllPostImages() {
        return postImageRepository.findAll();
    }


    public PostImage updatePostImage(Long id, PostImage updatedImage) {
        return postImageRepository.findById(id)
                .map(postImage -> {
                    postImage.setImageUrl(updatedImage.getImageUrl());
                    postImage.setPost(updatedImage.getPost());
                    return postImageRepository.save(postImage);
                })
                .orElseThrow(() -> new NotFoundException("PostImage not found with id " + id));
    }

    public List<PostImage> getImagesByPostId(Long postId) {
        return postImageRepository.findByPostId(postId);
    }

    public PostImage addImage(PostImage postImage) {
        return postImageRepository.save(postImage);
    }

    public void deleteImage(Long id) {
        postImageRepository.deleteById(id);
    }
}
