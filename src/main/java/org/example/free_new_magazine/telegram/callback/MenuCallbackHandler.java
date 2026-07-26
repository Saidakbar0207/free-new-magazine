package org.example.free_new_magazine.telegram.callback;

import lombok.RequiredArgsConstructor;
import org.example.free_new_magazine.telegram.commands.AdminBroadcastCommand;
import org.example.free_new_magazine.telegram.commands.AdminStatsCommand;
import org.example.free_new_magazine.telegram.commands.CategoriesCommand;
import org.example.free_new_magazine.telegram.commands.OffCommand;
import org.example.free_new_magazine.telegram.commands.PostCommand;
import org.example.free_new_magazine.telegram.security.AdminAccessGuard;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@RequiredArgsConstructor
public class MenuCallbackHandler implements CallbackHandler {

    private final CategoriesCommand categoriesCommand;
    private final PostCommand postCommand;
    private final OffCommand offCommand;

    private final AdminBroadcastCommand adminBroadcastCommand;
    private final AdminStatsCommand adminStatsCommand;
    private final AdminAccessGuard adminAccessGuard;

    @Override
    public boolean supports(String data) {
        return data != null && data.startsWith("menu:");
    }

    @Override
    public void handle(Update update) {
        String data = update.getCallbackQuery().getData();
        Long chatId = update.getCallbackQuery().getMessage().getChatId();

        switch (data) {
            case "menu:categories" -> categoriesCommand.execute(update);
            case "menu:latest" -> postCommand.execute(update);
            case "menu:off" -> offCommand.execute(update);

            case "menu:admin:broadcast" -> {
                adminAccessGuard.check(chatId);
                adminBroadcastCommand.execute(update);
            }
            case "menu:admin:stats" -> {
                adminAccessGuard.check(chatId);
                adminStatsCommand.execute(update);
            }

            default -> throw new IllegalArgumentException("Unknown menu callback: " + data);
        }
    }

    @Override
    public String getCallbackCode() {
        return "";
    }
}
