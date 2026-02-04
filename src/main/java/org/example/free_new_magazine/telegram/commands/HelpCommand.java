package org.example.free_new_magazine.telegram.commands;

import lombok.RequiredArgsConstructor;
import org.example.free_new_magazine.telegram.service.TelegramSenderService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@RequiredArgsConstructor
public class HelpCommand implements BotCommand {

    private final TelegramSenderService sender;

    @Override
    public void execute(Update update) {
        Long chatId = update.getMessage().getChatId();
        sender.send(chatId,
                "ℹ️ <b>Yordam</b>\n\n" +
                        "• /start — menyu\n" +
                        "• 📰 Postlar — so‘nggi postlar\n" +
                        "• 📂 Kategoriyalar — obuna bo‘lish\n" +
                        "• 🔔 Obuna — umumiy obunani yoq/o‘chir\n"
        );
    }

    @Override
    public String getCommandName() {
        return "ℹ️ Yordam";
    }
}
