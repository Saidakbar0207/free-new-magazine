package org.example.free_new_magazine.controller;

import lombok.RequiredArgsConstructor;
import org.example.free_new_magazine.entity.PostImage;
import org.example.free_new_magazine.service.PostImageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/post-images")
@RequiredArgsConstructor
public class PostImageController {

    private final PostImageService service;

    @PostMapping
    public ResponseEntity<PostImage> addImage(@RequestBody PostImage postImage) {
        PostImage saved = service.addImage(postImage);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @GetMapping("/post/{postId}")
    public ResponseEntity<List<PostImage>> getImagesByPost(@PathVariable Long postId) {
        List<PostImage> images = service.getImagesByPostId(postId);
        return ResponseEntity.ok(images);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteImage(@PathVariable Long id) {
        service.deleteImage(id);
        return ResponseEntity.noContent().build();
    }
}
