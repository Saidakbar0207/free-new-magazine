package org.example.free_new_magazine.service;

import lombok.RequiredArgsConstructor;
import org.example.free_new_magazine.dto.PostTagDTO;
import org.example.free_new_magazine.entity.*;
import org.example.free_new_magazine.exception.ForbiddenException;
import org.example.free_new_magazine.exception.NotFoundException;
import org.example.free_new_magazine.mapper.PostTagMapper;
import org.example.free_new_magazine.repository.PostRepository;
import org.example.free_new_magazine.repository.PostTagRepository;
import org.example.free_new_magazine.repository.TagRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostTagService {

    private final PostTagRepository postTagRepository;
    private final PostTagMapper postTagMapper;
    private final PostRepository postRepository;
    private final TagRepository tagRepository;
    private final CurrentUserService currentUserService;




    public PostTagDTO addPostTag(PostTagDTO dto) {
        if(dto.getPostId() == null || dto.getTagId() == null){
            throw new NotFoundException("Post or Tag not found");
        }
        User user = currentUserService.getCurrentUser();
        Post post =postRepository.findById(dto.getPostId())
                .orElseThrow(() -> new NotFoundException("Post not found with id: " + dto.getPostId()));
        boolean isOwner = post.getAuthor().getId().equals(user.getId());
        boolean isAdmin = user.getRole() == Role.ROLE_ADMIN;
        if(!isOwner && !isAdmin) {
            throw new ForbiddenException("You are not allowed to add tags to this post");
        }
        Tag tag = tagRepository.findById(dto.getTagId())
                .orElseThrow(() -> new NotFoundException("Tag not found with id: " + dto.getTagId()));

        PostTag postTag = new PostTag();
        postTag.setTag(tag);
        postTag.setPost(post);

        PostTag savedPostTag = postTagRepository.save(postTag);

       return postTagMapper.toDTO(savedPostTag);
    }


    public List<PostTagDTO> getTagsByPostId(Long postId) {
        List<PostTag> tags = postTagRepository.findByPostId(postId);

        return postTagMapper.toDTO(tags);
    }

    public void deletePostTag(Long id) {
            User me = currentUserService.getCurrentUser();

            PostTag pt = postTagRepository.findById(id)
                    .orElseThrow(() -> new NotFoundException("PostTag not found"));

            Post post = pt.getPost();

            boolean owner = post.getAuthor() != null && post.getAuthor().getId().equals(me.getId());
            boolean admin = me.getRole() == Role.ROLE_ADMIN;
            if (!owner && !admin) throw new ForbiddenException("No access");

            postTagRepository.delete(pt);
        }
}
