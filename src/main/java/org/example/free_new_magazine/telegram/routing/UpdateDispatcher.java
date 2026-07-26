package org.example.free_new_magazine.telegram.dispatcher;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.free_new_magazine.telegram.bot.FreeNewsTelegramBot;
import org.example.free_new_magazine.telegram.callback.CategoryCallbackHandler;
import org.example.free_new_magazine.telegram.commands.BotCommand;
import org.example.free_new_magazine.telegram.commands.CommandContainer;
import org.example.free_new_magazine.telegram.service.TelegramUserService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@RequiredArgsConstructor
@Slf4j
public class UpdateDispatcher {

    private final TelegramUserService telegramUserService;
    private final CommandContainer commandContainer;
    private final CategoryCallbackHandler categoryCallbackHandler;

    public void dispatch(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String text = update.getMessage().getText();
            telegramUserService.touch(update.getMessage());
            commandContainer.retrieveCommand(text).execute(update);
        } else if (update.hasCallbackQuery()) {

            categoryCallbackHandler.handle(update);
        }
    }
    private void handleTextMessage(Update update, FreeNewsTelegramBot bot) {
        String messageText = update.getMessage().getText();
        telegramUserService.touch(update.getMessage());


        BotCommand command = commandContainer.retrieveCommand(messageText);
        command.execute(update);
    }
}
