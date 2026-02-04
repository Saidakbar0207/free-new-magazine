package org.example.free_new_magazine.telegram.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class TelegramBotConfig {
    @Value("${telegram.bot.username}")
    private String username;

    @Value("${telegram.bot.token}")
    private String token;

    @Value("${telegram.bot.admin-ids}")
    private String adminIds;

    @Value("${telegram.bot.url}")
    private String botUrl;

    public boolean isAdmin(Long chatId) {
        if(adminIds == null || adminIds.isBlank()) return false;
        for(String s : adminIds.split(",")) {
            if(s.trim().equals(String.valueOf(chatId))) return true;
        }
        return false;
    }

}
