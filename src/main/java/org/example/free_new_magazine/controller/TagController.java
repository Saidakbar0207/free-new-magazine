package org.example.free_new_magazine.controller;

import lombok.RequiredArgsConstructor;
import org.example.free_new_magazine.dto.TagDTO;
import org.example.free_new_magazine.entity.Tag;
import org.example.free_new_magazine.mapper.TagMapper;
import org.example.free_new_magazine.service.TagService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/tags")
@RequiredArgsConstructor
public class TagController {

    private final TagService service;
    private final TagMapper mapper;

    @PostMapping
    public ResponseEntity<TagDTO> createTag(@Valid @RequestBody TagDTO dto) {
        Tag toSave = mapper.toEntity(dto);
        Tag saved = service.createTag(toSave);
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toDTO(saved));
    }

    @GetMapping
    public ResponseEntity<List<TagDTO>> getAllTags() {
        List<TagDTO> result = service.getAllTags().stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TagDTO> getTagById(@PathVariable Long id) {
        Tag tag = service.getTagById(id);
        return ResponseEntity.ok(mapper.toDTO(tag));
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<TagDTO> getTagByName(@PathVariable String name) {
        Tag tag = service.getTagByName(name);
        return ResponseEntity.ok(mapper.toDTO(tag));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTag(@PathVariable Long id) {
        service.deleteTag(id);
        return ResponseEntity.noContent().build();
    }
}
