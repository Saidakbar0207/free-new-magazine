package org.example.free_new_magazine.telegram.commands.menu_commands;

import lombok.RequiredArgsConstructor;
import org.example.free_new_magazine.telegram.commands.AdminStatsCommand;
import org.example.free_new_magazine.telegram.commands.BotCommand;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@RequiredArgsConstructor
public class Stat implements BotCommand {

    private final AdminStatsCommand adminStatsCommand;

    @Override
    public void execute(Update update) {
        adminStatsCommand.execute(update);
    }

    @Override
    public String getCommandName() {
        return "📊 Stat";
    }
}
