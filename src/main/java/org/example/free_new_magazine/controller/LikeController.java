package org.example.free_new_magazine.controller;

import lombok.RequiredArgsConstructor;
import org.example.free_new_magazine.dto.LikeDTO;
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
    public ResponseEntity<LikeDTO> likePost(@RequestParam Long postId) {
        return ResponseEntity.status(HttpStatus.CREATED).body(likeService.likePost(postId));
    }

    @DeleteMapping
    public ResponseEntity<Void> unlikePost(@RequestParam Long postId) {
        likeService.unlikePost(postId);
        return ResponseEntity.noContent().build();
    }


    @GetMapping("/post/{postId}")
    public ResponseEntity<Long> countLikesByPost(@PathVariable Long postId) {
        return ResponseEntity.ok(likeService.countLikesByPost(postId));
    }


    @GetMapping("/user")
    public List<LikeDTO> getLikesByUser() {
        return likeService.getLikesByUser();
    }
}
