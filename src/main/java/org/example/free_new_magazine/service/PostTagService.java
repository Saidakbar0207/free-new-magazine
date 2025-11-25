package org.example.free_new_magazine.service;

import org.example.free_new_magazine.entity.PostTag;
import org.example.free_new_magazine.repository.PostTagRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostTagService {

    private final PostTagRepository postTagRepository;

    public PostTagService(PostTagRepository postTagRepository) {
        this.postTagRepository = postTagRepository;
    }

    public List<PostTag> getAllPostTags() {
        return postTagRepository.findAll();
    }

    public PostTag addPostTag(PostTag postTag) {
        return postTagRepository.save(postTag);
    }


    public List<PostTag> getTagsByPostId(Long postId) {
        return postTagRepository.findByPostId(postId);
    }

    public void deletePostTag(Long id) {
        postTagRepository.deleteById(id);
    }
}
