package org.example.free_new_magazine.controller;

import lombok.RequiredArgsConstructor;
import org.example.free_new_magazine.dto.FollowDTO;
import org.example.free_new_magazine.entity.Follow;
import org.example.free_new_magazine.mapper.FollowMapper;
import org.example.free_new_magazine.service.FollowService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/follows")
@RequiredArgsConstructor
public class FollowController {

    private final FollowService followService;

    @GetMapping
    public List<FollowDTO> getAllFollows() {
        return followService.getAllFollows();
    }

    @PostMapping("/{followingId}")
    public ResponseEntity<FollowDTO> follow(@RequestBody Long followingId) {
        return ResponseEntity.status(HttpStatus.CREATED).
                body(followService.follow(followingId));
    }
    @DeleteMapping("/{followingId}")
    public ResponseEntity<Void> unfollow(@PathVariable Long followingId) {
        followService.unfollow(followingId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/me/followers")
    public List<FollowDTO> myFollowers() {
        return followService.getMyFollowers();
    }

    @GetMapping("/me/following")
    public List<FollowDTO> myFollowing() {
        return followService.getMyFollowing();
    }
}
