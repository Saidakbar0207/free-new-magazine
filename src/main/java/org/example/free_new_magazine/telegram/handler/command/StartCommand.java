package org.example.free_new_magazine.telegram.commands;

import lombok.RequiredArgsConstructor;
import org.example.free_new_magazine.telegram.factory.MenuFactory;
import org.example.free_new_magazine.telegram.service.TelegramSenderService;
import org.example.free_new_magazine.telegram.service.TelegramUserService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@RequiredArgsConstructor
public class StartCommand implements BotCommand {
    private final TelegramUserService telegramUserService;
    private final TelegramSenderService telegramSenderService;
    private final MenuFactory menuFactory;

    @Override
    public void execute(Update update) {
        var msg = update.getMessage();
        telegramUserService.getOrCreate(
                msg.getChatId(),
                msg.getFrom().getUserName(),
                msg.getFrom().getFirstName(),
                msg.getFrom().getLanguageCode()
        );
        telegramSenderService.sendWithReplyKeyboard(msg.getChatId(), "Assalomu alaykum!\n" +
                "Bu bot yangi postlar chiqsa sizga yuboradi.\n",
                menuFactory.mainMenu());
    }


    @Override
    public String getCommandName() { return "/start"; }
}