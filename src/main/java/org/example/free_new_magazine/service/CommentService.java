package org.example.free_new_magazine.service;

import lombok.RequiredArgsConstructor;
import org.example.free_new_magazine.dto.CommentDTO;
import org.example.free_new_magazine.entity.Comment;
import org.example.free_new_magazine.entity.Post;
import org.example.free_new_magazine.entity.Role;
import org.example.free_new_magazine.entity.User;
import org.example.free_new_magazine.exception.ResourceNotFoundException;
import org.springframework.security.access.AccessDeniedException;
import org.example.free_new_magazine.mapper.CommentMapper;
import org.example.free_new_magazine.repository.CommentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final CommentMapper  commentMapper;
    private final CurrentUserService currentUserService;
    private final AuditLogService auditLogService;
    private final PostService postService;



    public List<CommentDTO> getAllComments() {
        User user = currentUserService.getCurrentUser();
        if(user.getRole() != Role.ROLE_ADMIN) {
            throw new AccessDeniedException("Only ADMIN can manage comments");
        }
        return commentRepository.findAll()
                .stream()
                .map(commentMapper::toDTO)
                .collect(Collectors.toList());
    }

    public CommentDTO getCommentById(Long id) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Comment not found with id " + id));
        return commentMapper.toDTO(comment);
    }



    public CommentDTO createComment(CommentDTO commentDTO) {
        User user =  currentUserService.getCurrentUser();
        Post post = postService.getPostEntityById(commentDTO.getPostId());
        Comment comment = commentMapper.toEntity(commentDTO);
        comment.setUser(user);
        comment.setPost(post);
        Comment saved =  commentRepository.save(comment);
        auditLogService.log("CREATE_COMMENT", "/comments");

        return commentMapper.toDTO(saved);
    }

    public CommentDTO updateComment(Long id, CommentDTO commentDTO){
        User user = currentUserService.getCurrentUser();
        Comment  comment = commentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Comment not found with id " + id));

        if(!comment.getUser().getId().equals(user.getId()) && user.getRole() != Role.ROLE_ADMIN) {
            throw new AccessDeniedException("You are not allowed to update this comment");
        }
        comment.setContent(commentDTO.getContent());
        Comment updated = commentRepository.save(comment);
        auditLogService.log("UPDATE_COMMENT", "/comments");

        return commentMapper.toDTO(updated);

    }


    public void deleteComment(Long id) {
        User user = currentUserService.getCurrentUser();
        Comment comment = commentRepository.findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException("Comment not found with id " + id));

        if(!comment.getUser().getId().equals(user.getId()) && user.getRole() != Role.ROLE_ADMIN) {
            throw new AccessDeniedException("You are not allowed to delete this comment ");
        }
        commentRepository.deleteById(id);
        auditLogService.log("DELETE_COMMENT", "/comments");
    }

    public List<CommentDTO> getCommentsByPostId(Long postId) {
        return commentRepository.findByPostId(postId).stream()
                .map(commentMapper::toDTO)
                .toList();
    }
}
