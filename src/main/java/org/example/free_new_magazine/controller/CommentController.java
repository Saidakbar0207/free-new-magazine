package org.example.free_new_magazine.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.free_new_magazine.dto.CommentDTO;
import org.example.free_new_magazine.entity.Comment;
import org.example.free_new_magazine.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @GetMapping
    public List<CommentDTO> getAllComments() {
        return commentService.getAllComments();
    }


    @GetMapping("/{id}")
    public ResponseEntity<CommentDTO> getCommentById(@PathVariable Long id) {
        return ResponseEntity.ok(commentService.getCommentById(id));
    }


    @GetMapping("/post/{postId}")
    public List<CommentDTO> getCommentsByPost(@PathVariable Long postId) {
        return commentService.getCommentsByPostId(postId);
    }


    @PostMapping
    public ResponseEntity<CommentDTO> createComment(
            @Valid @RequestBody CommentDTO commentDTO) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(commentService.createComment(commentDTO));
    }


    @PutMapping("/{id}")
    public ResponseEntity<CommentDTO> updateComment(
            @Valid @PathVariable Long id,
            @RequestBody CommentDTO commentDTO
    ) {
        return ResponseEntity.ok(commentService.updateComment(id, commentDTO));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long id) {
        commentService.deleteComment(id);
        return ResponseEntity.noContent().build();
    }
}