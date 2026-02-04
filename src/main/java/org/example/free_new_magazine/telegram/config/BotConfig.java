package org.example.free_new_magazine.telegram.config;

import org.example.free_new_magazine.telegram.bot.FreeNewsTelegramBot;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@Configuration
public class BotConfig {
    @Bean
    public TelegramBotsApi telegramBotsApi(FreeNewsTelegramBot myTelegramBot) throws TelegramApiException {
        return new TelegramBotsApi(DefaultBotSession.class) {{
            registerBot(myTelegramBot);
        }};
    }
}
