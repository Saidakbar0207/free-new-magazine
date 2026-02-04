package org.example.free_new_magazine.telegram.commands;

import lombok.RequiredArgsConstructor;
import org.example.free_new_magazine.telegram.service.TelegramSenderService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@RequiredArgsConstructor
public class DefaultCommandHandler implements BotCommand {

    private final TelegramSenderService senderService;

    @Override
    public void execute(Update update) {
        Long chatId = update.getMessage().getChatId();
        String text = "Kechirasiz, men bu buyruqni tushunmadim. 🤖\n" +
                "Mavjud buyruqlarni ko'rish uchun /start ni bosing.";

        senderService.send(chatId, text);
    }

    @Override
    public String getCommandName() {

        return "DEFAULT";
    }
}
