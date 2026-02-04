package org.example.free_new_magazine.telegram.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.free_new_magazine.entity.Post;
import org.example.free_new_magazine.entity.TelegramUserCategory;
import org.example.free_new_magazine.event.PostPublishedEvent;
import org.example.free_new_magazine.repository.PostRepository;
import org.example.free_new_magazine.repository.TelegramUserCategoryRepository;
import org.example.free_new_magazine.telegram.config.TelegramBotConfig;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
@RequiredArgsConstructor
@Slf4j
public class TelegramNotificationService {

    private final TelegramUserCategoryRepository subscriptionRepository;
    private final PostRepository postRepository;
    private final TelegramSenderService senderService;
    private final TelegramBotConfig botConfig;

    @Async
    @EventListener
    @Transactional(readOnly = true)
    public void handlePostPublishedEvent(PostPublishedEvent event) {
        log.info("Yangi post uchun bildirishnoma yuborilmoqda: PostID={}", event.postId());

        Post post = postRepository.findById(event.postId())
                .orElseThrow(() -> new RuntimeException("Post topilmadi"));


        List<TelegramUserCategory> subscriptions =
                subscriptionRepository.findAllActiveWithUsersAndCategories(post.getCategory().getId());

        String messageText = format(post);

        for (TelegramUserCategory sub : subscriptions) {

            try {
                senderService.send(sub.getTelegramUser().getChatId(), messageText);

                Thread.sleep(35);
            } catch (Exception e) {
                log.error("Foydalanuvchiga yuborishda xato: {}", sub.getTelegramUser().getChatId());
            }
        }
    }

    private String format(Post post) {
        String url = String.format(botConfig.getBotUrl(),post.getId());
        return String.format(
                "🔥 <b>YANGI POST!</b>\n\n" +
                        "📰 <b>%s</b>\n\n" +
                        "%s\n\n" +
                        "👉 <a href='%s'>Batafsil o'qish</a>",
                post.getTitle(),
                post.getContent().length() > 200
                        ? post.getContent().substring(0, 200) + "..."
                        : post.getContent(),
                url
        );
    }

}