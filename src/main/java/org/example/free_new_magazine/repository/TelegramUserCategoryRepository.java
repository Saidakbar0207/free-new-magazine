package org.example.free_new_magazine.repository;

import org.example.free_new_magazine.entity.Category;
import org.example.free_new_magazine.entity.TelegramUser;
import org.example.free_new_magazine.entity.TelegramUserCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TelegramUserCategoryRepository extends JpaRepository<TelegramUserCategory, Long> {

    @Query("SELECT tuc FROM TelegramUserCategory tuc " +
            "JOIN FETCH tuc.telegramUser " +
            "JOIN FETCH tuc.category " +
            "WHERE tuc.category.id = :categoryId AND tuc.active = true")
    List<TelegramUserCategory> findAllActiveWithUsersAndCategories(@Param("categoryId") Long categoryId);


    Optional<TelegramUserCategory> findByTelegramUserAndCategory(TelegramUser user, Category category);


    List<TelegramUserCategory> findAllByTelegramUserAndActiveTrue(TelegramUser user);

    long countByActiveTrue();


}
