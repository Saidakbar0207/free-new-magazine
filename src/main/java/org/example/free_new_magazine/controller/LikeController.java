package org.example.free_new_magazine.controller;

import lombok.RequiredArgsConstructor;
import org.example.free_new_magazine.entity.Like;
import org.example.free_new_magazine.service.LikeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/likes")
@RequiredArgsConstructor
public class LikeController {

    private final LikeService likeService;


    @PostMapping
    public ResponseEntity<Like> likePost(
            @RequestParam Long userId,
            @RequestParam Long postId
    ) {
        Like like = likeService.likePost(userId, postId);
        return ResponseEntity.status(HttpStatus.CREATED).body(like);
    }


    @DeleteMapping
    public ResponseEntity<Void> unlikePost(
            @RequestParam Long userId,
            @RequestParam Long postId
    ) {
        likeService.unlikePost(userId, postId);
        return ResponseEntity.noContent().build();
    }


    @GetMapping("/post/{postId}")
    public ResponseEntity<Long> countLikesByPost(@PathVariable Long postId) {
        return ResponseEntity.ok(likeService.countLikesByPost(postId));
    }


    @GetMapping("/user/{userId}")
    public List<Like> getUserLikes(@PathVariable Long userId) {
        return likeService.getLikesByUser(userId);
    }
}
