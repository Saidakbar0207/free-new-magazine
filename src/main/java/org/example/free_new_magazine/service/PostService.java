package org.example.free_new_magazine.service;

import lombok.RequiredArgsConstructor;
import org.example.free_new_magazine.dto.PostDTO;
import org.example.free_new_magazine.entity.Category;
import org.example.free_new_magazine.entity.Post;
import org.example.free_new_magazine.exception.ResourceNotFoundException;
import org.example.free_new_magazine.mapper.PostMapper;
import org.example.free_new_magazine.repository.CategoryRepository;
import org.example.free_new_magazine.repository.PostRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final PostMapper postMapper;


    public List<PostDTO> getAllPosts() {
        return postRepository.findAll()
                .stream()
                .map(postMapper::toDTO)
                .collect(Collectors.toList());
    }

    public PostDTO getPostById(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found with id: " + id));
        return postMapper.toDTO(post);
    }


    public PostDTO createPost(PostDTO postDTO) {
        Post post = postMapper.toEntity(postDTO);
        Post savedPost = postRepository.save(post);
        return postMapper.toDTO(savedPost);
    }


    public PostDTO updatePost(Long id, PostDTO postDTO) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found with id: " + id));


        post.setTitle(postDTO.getTitle());
        post.setContent(postDTO.getContent());
        if (postDTO.getCategoryId() != null) {
            Category category = new Category();
            category.setId(postDTO.getCategoryId());
            post.setCategory(category);
        } else {
            post.setCategory(null);
        }

        Post updatedPost = postRepository.save(post);
        return postMapper.toDTO(updatedPost);
    }


    public void deletePost(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found with id: " + id));
        postRepository.delete(post);
    }
}
