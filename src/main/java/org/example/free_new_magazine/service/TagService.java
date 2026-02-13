package org.example.free_new_magazine.service;

import lombok.RequiredArgsConstructor;
import org.example.free_new_magazine.entity.Tag;
import org.example.free_new_magazine.exception.NotFoundException;
import org.example.free_new_magazine.repository.TagRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TagService {

    private final TagRepository repository;

    public Tag createTag(Tag tag) {
        return repository.save(tag);
    }

    public List<Tag> getAllTags() {
        return repository.findAll();
    }

    public Tag getTagById(Long id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundException("Tag not found"));
    }

    @Transactional
    public void deleteTag(Long id) {
        repository.deleteById(id);
    }

    public Tag getTagByName(String name) {
        return repository.findByName(name).orElseThrow(() -> new NotFoundException("Tag not found"));
    }
}
