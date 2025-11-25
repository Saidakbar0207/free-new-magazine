package org.example.free_new_magazine.controller;

import lombok.RequiredArgsConstructor;
import org.example.free_new_magazine.entity.PostTag;
import org.example.free_new_magazine.service.PostTagService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/post-tags")
@RequiredArgsConstructor
public class PostTagController {

    private final PostTagService service;

    @PostMapping
    public ResponseEntity<PostTag> addPostTag(@RequestBody PostTag postTag) {
        PostTag saved = service.addPostTag(postTag);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @GetMapping("/post/{postId}")
    public ResponseEntity<List<PostTag>> getTagsByPost(@PathVariable Long postId) {
        List<PostTag> tags = service.getTagsByPostId(postId);
        return ResponseEntity.ok(tags);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePostTag(@PathVariable Long id) {
        service.deletePostTag(id);
        return ResponseEntity.noContent().build();
    }
}
