package org.example.free_new_magazine.repository;

import org.example.free_new_magazine.entity.TelegramUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TelegramUserRepository extends JpaRepository<TelegramUser,Long> {

    Optional<TelegramUser>  findByChatId(Long chatId);

    List<TelegramUser> findAllByActiveTrueAndSubscribedTrue();

    List<TelegramUser> findAllByActiveTrue();

    long countByActiveTrue();
    
}
