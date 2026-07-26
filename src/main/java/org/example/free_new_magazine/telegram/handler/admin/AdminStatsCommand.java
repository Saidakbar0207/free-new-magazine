package org.example.free_new_magazine.telegram.handler.command;

import lombok.RequiredArgsConstructor;
import org.example.free_new_magazine.repository.TelegramUserCategoryRepository;
import org.example.free_new_magazine.repository.TelegramUserRepository;
import org.example.free_new_magazine.telegram.config.TelegramBotConfig;
import org.example.free_new_magazine.telegram.service.TelegramSenderService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@RequiredArgsConstructor
public class AdminStatsCommand implements BotCommand{

    private final TelegramBotConfig botConfig;
    private final TelegramSenderService senderService;
    private final TelegramUserRepository userRepository;
    private final TelegramUserCategoryRepository subscriptionRepository;


    @Override
    public void execute(Update update) {
        Long chatId = update.getMessage().getChatId();

        if(!botConfig.isAdmin(chatId)){
            senderService.send(chatId, "⛔ Siz admin emassiz");
            return;
        }
        long users = userRepository.count();
        long activeUsers = userRepository.countByActiveTrue();
        long subs = subscriptionRepository.count();

        String text =
                "📊 STATISTIKA\n\n" +
                        "👤 Jami user: " + users + "\n" +
                        "✅ Active user: " + activeUsers + "\n" +
                        "📌 Obunalar: " + subs;

        senderService.send(chatId,text);

    }

    @Override
    public String getCommandName() {
        return "/stat";
    }
}
