package org.example.free_new_magazine.controller;

import lombok.RequiredArgsConstructor;
import org.example.free_new_magazine.dto.PostResponseDTO;
import org.example.free_new_magazine.service.SavedPostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/saved-posts")
@RequiredArgsConstructor
public class SavedPostController {

    private final SavedPostService service;

    @GetMapping("/me/ids")
    public ResponseEntity<List<Long>> mySavedPostIds() {
        return ResponseEntity.ok(service.getMySavedPostIds());
    }


    @GetMapping("/me")
    public ResponseEntity<List<PostResponseDTO>> mySavedPosts() {
        return ResponseEntity.ok(service.getMySavedPosts());
    }


    @PostMapping("/{postId}")
    public ResponseEntity<Void> save(@PathVariable Long  postId) {
       service.save(postId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> deleteSavedPost(@PathVariable Long postId) {
        service.unsave(postId);
        return ResponseEntity.noContent().build();
    }
}
