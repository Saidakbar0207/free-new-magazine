package org.example.free_new_magazine.repository;

import org.example.free_new_magazine.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface CategoryRepository extends JpaRepository<Category,Long> {

}
