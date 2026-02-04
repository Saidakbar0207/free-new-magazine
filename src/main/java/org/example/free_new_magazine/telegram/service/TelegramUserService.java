package org.example.free_new_magazine.telegram.service;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.free_new_magazine.entity.TelegramUser;
import org.example.free_new_magazine.repository.TelegramUserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class TelegramUserService {

    private final TelegramUserRepository repository;

    @Transactional
    public void toggleUserActivity(Message msg, boolean isActive) {
        Long chatId = msg.getChatId();

        repository.findByChatId(chatId).ifPresentOrElse(
                user -> {
                    if (user.isActive() != isActive) {
                        user.setActive(isActive);
                        repository.save(user);
                    }
                },
                () -> {
                    if (isActive) {
                        saveNewUser(msg);
                    }
                }
        );
    }

    @Transactional
    public void updateSubscription(Long chatId, boolean isSubscribed) {
        repository.findByChatId(chatId).ifPresent(user -> {
            user.setSubscribed(isSubscribed);
            user.setActive(isSubscribed);
            repository.save(user);
        });
    }

    private void saveNewUser(Message msg) {
        TelegramUser newUser = TelegramUser.builder()
                .chatId(msg.getChatId())
                .username(msg.getFrom().getUserName())
                .firstName(msg.getFrom().getFirstName())
                .language(msg.getFrom().getLanguageCode())
                .subscribed(true)
                .active(true)
                .createdAt(LocalDateTime.now())
                .build();
        repository.save(newUser);
        log.info("Yangi foydalanuvchi ro'yxatga olindi: {}", msg.getChatId());
    }

    public List<TelegramUser> getAllActiveSubscribers() {
        return repository.findAllByActiveTrueAndSubscribedTrue();
    }

    @Transactional
    public void touch(Message message) {
        Long chatId = message.getChatId();
        String username = message.getFrom().getUserName();
        String firstName = message.getFrom().getFirstName();

        repository.findByChatId(chatId).ifPresentOrElse(
                user -> {
                    boolean needsUpdate = false;

                    if (!user.isActive()) {
                        user.setActive(true);
                        needsUpdate = true;
                    }


                    if (username != null && !username.equals(user.getUsername())) {
                        user.setUsername(username);
                        needsUpdate = true;
                    }

                    if (needsUpdate) {
                        repository.save(user);
                        log.debug("Foydalanuvchi holati yangilandi: {}", chatId);
                    }
                },
                () -> {
                    log.info("Yangi foydalanuvchi aniqlandi: {}", chatId);
                    saveNewUser(message);
                }
        );
    }

    @Transactional
    public void getOrCreate(Long chatId, String userName, @NonNull String firstName, String languageCode) {
        Optional<TelegramUser> userOptional = repository.findByChatId(chatId);

        if (userOptional.isPresent()) {
            TelegramUser user = userOptional.get();

            user.setUsername(userName);
            user.setFirstName(firstName);
            user.setLanguage(languageCode);
            user.setActive(true);
            user.setSubscribed(true);
            repository.save(user);
            log.info("Mavjud foydalanuvchi yangilandi: {}", chatId);
        } else {

            TelegramUser newUser = TelegramUser.builder()
                    .chatId(chatId)
                    .username(userName)
                    .firstName(firstName)
                    .language(languageCode != null ? languageCode : "uz")
                    .active(true)
                    .subscribed(true)
                    .build();
            repository.save(newUser);
            log.info("Yangi foydalanuvchi ro'yxatga olindi: {}", chatId);
        }
    }

    @Transactional
    public void unsubscribe(Long chatId) {
        repository.findByChatId(chatId).ifPresentOrElse(user -> {
            user.setSubscribed(false);
            repository.save(user);
            log.info("Foydalanuvchi obunani to'xtatdi: {}", chatId);
        }, () -> log.warn("Obunani to'xtatmoqchi bo'lgan foydalanuvchi topilmadi: {}", chatId));
    }

    public String getLanguageCode(Long chatId) {
        return repository.findByChatId(chatId)
                .map(TelegramUser::getLanguage)
                .orElse("uz");
    }
}