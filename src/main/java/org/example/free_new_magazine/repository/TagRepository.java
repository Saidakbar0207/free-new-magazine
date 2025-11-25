package org.example.free_new_magazine.repository;

import org.example.free_new_magazine.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TagRepository   extends JpaRepository<Tag,Long> {
    Optional<Tag> findByName(String name);

}
