package org.example.free_new_magazine.telegram.commands;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.free_new_magazine.telegram.service.TelegramSenderService;
import org.example.free_new_magazine.telegram.service.TelegramUserService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@RequiredArgsConstructor
@Slf4j
public class OffCommand implements BotCommand {

    private final TelegramSenderService telegramSenderService;
    private final TelegramUserService telegramUserService;

    @Override
    public void execute(Update update) {
        Long chatId = update.getMessage().getChatId();

        try {

            telegramUserService.unsubscribe(chatId);


            telegramSenderService.send(
                    chatId,
                    "🔕 <b>Obuna to‘xtatildi.</b>\n\nSizga yangi postlar haqida bildirishnoma kelmaydi. /start yozib qayta yoqishingiz mumkin."
            );

            log.info("User unsubscribed: {}", chatId);
        } catch (Exception e) {
            log.error("Unsubscribe error for chat {}: {}", chatId, e.getMessage());
        }
    }

    @Override
    public String getCommandName() {
        return "/off";
    }
}