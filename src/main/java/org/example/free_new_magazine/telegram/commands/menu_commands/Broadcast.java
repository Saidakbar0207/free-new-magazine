package org.example.free_new_magazine.telegram.commands.menu_commands;

import lombok.RequiredArgsConstructor;
import org.example.free_new_magazine.telegram.commands.AdminBroadcastCommand;
import org.example.free_new_magazine.telegram.commands.BotCommand;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@RequiredArgsConstructor
public class Broadcast implements BotCommand {

    private final AdminBroadcastCommand adminBroadcastCommand;

    @Override
    public void execute(Update update) {
        adminBroadcastCommand.execute(update);

    }

    @Override
    public String getCommandName() {
        return "📣 Broadcast";
    }
}
