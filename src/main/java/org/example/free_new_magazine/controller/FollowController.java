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
    public List<Follow> getAllFollows() {
        return followService.getAllFollows();
    }

    @PostMapping
    public ResponseEntity<FollowDTO> createFollow(@RequestBody FollowDTO follow) {

        FollowDTO savedFollow = followService.createFollow(follow);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedFollow);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFollow(@PathVariable Long id) {
        followService.deleteFollow(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/followers/{userId}")
    public List<Follow> getFollowers(@PathVariable Long userId) {
        return followService.getFollowers(userId);
    }

    @GetMapping("/following/{userId}")
    public List<Follow> getFollowing(@PathVariable Long userId) {
        return followService.getFollowing(userId);
    }
}
