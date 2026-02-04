package org.example.free_new_magazine.repository;

import org.example.free_new_magazine.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findAllByIsActiveTrue();
    Optional<Category> findByCode(String code);
}
