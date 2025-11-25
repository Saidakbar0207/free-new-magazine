package org.example.free_new_magazine.controller;

import lombok.RequiredArgsConstructor;
import org.example.free_new_magazine.entity.SavedPost;
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

    @PostMapping
    public ResponseEntity<SavedPost> savePost(@RequestBody SavedPost savedPost) {
        SavedPost saved = service.savePost(savedPost);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<SavedPost>> getSavedPostsByUser(@PathVariable Long userId) {
        List<SavedPost> posts = service.getSavedPostsByUser(userId);
        return ResponseEntity.ok(posts);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSavedPost(@PathVariable Long id) {
        service.deleteSavedPost(id);
        return ResponseEntity.noContent().build();
    }
}
