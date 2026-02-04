package org.example.free_new_magazine.telegram.bot;

import lombok.RequiredArgsConstructor;
import org.example.free_new_magazine.telegram.config.TelegramBotConfig;
import org.example.free_new_magazine.telegram.dispatcher.UpdateDispatcher;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class FreeNewsTelegramBot extends TelegramLongPollingBot {

    private final TelegramBotConfig botConfig;
    private final UpdateDispatcher updateDispatcher;


    public FreeNewsTelegramBot(TelegramBotConfig botConfig, UpdateDispatcher updateDispatcher) {
        super(buildOptions());
        this.botConfig = botConfig;
        this.updateDispatcher = updateDispatcher;
    }

    private static DefaultBotOptions buildOptions() {
        DefaultBotOptions options = new DefaultBotOptions();
        options.setGetUpdatesTimeout(50);
        return options;
    }

    @Override
    public void onUpdateReceived(Update update) {
        updateDispatcher.dispatch(update);
    }

    @Override
    public String getBotUsername() {
        return botConfig.getUsername();
    }

    @Override
    public String getBotToken() {
        return botConfig.getToken();
    }
}
