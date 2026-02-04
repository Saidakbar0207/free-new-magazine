package org.example.free_new_magazine.telegram.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.free_new_magazine.entity.Category;
import org.example.free_new_magazine.entity.TelegramUser;
import org.example.free_new_magazine.entity.TelegramUserCategory;
import org.example.free_new_magazine.repository.CategoryRepository;
import org.example.free_new_magazine.repository.TelegramUserCategoryRepository;
import org.example.free_new_magazine.repository.TelegramUserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class TelegramCategoryService {

    private final TelegramUserCategoryRepository subscriptionRepository;
    private final CategoryRepository categoryRepository;
    private final TelegramUserRepository userRepository;

    @Transactional
    public void subscribe(Long chatId, String categoryCode) {
        log.info("Obuna so'rovi: ChatID={}, Category={}", chatId, categoryCode);

        TelegramUser user = findUser(chatId);
        Category category = findCategory(categoryCode);

        TelegramUserCategory subscription = subscriptionRepository
                .findByTelegramUserAndCategory(user, category)
                .orElseGet(() -> TelegramUserCategory.builder()
                        .telegramUser(user)
                        .category(category)
                        .build());

        subscription.setActive(true);
        subscriptionRepository.save(subscription);
    }

    @Transactional
    public void unsubscribe(Long chatId, String categoryCode) {
        log.info("Obunani bekor qilish: ChatID={}, Category={}", chatId, categoryCode);

        TelegramUser user = findUser(chatId);
        Category category = findCategory(categoryCode);

        subscriptionRepository.findByTelegramUserAndCategory(user, category)
                .ifPresent(link -> {
                    link.setActive(false);
                    subscriptionRepository.save(link);
                });
    }
    private TelegramUser findUser(Long chatId) {
        return userRepository.findByChatId(chatId)
                .orElseThrow(() -> new RuntimeException("Foydalanuvchi topilmadi: " + chatId));
    }

    private Category findCategory(String code) {
        return categoryRepository.findByCode(code)
                .orElseThrow(() -> new RuntimeException("Kategoriya topilmadi: " + code));
    }
}