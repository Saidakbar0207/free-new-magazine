package org.example.free_new_magazine.telegram.commands.menu_commands;

import lombok.RequiredArgsConstructor;
import org.example.free_new_magazine.telegram.commands.BotCommand;
import org.example.free_new_magazine.telegram.commands.PostCommand;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@RequiredArgsConstructor
public class Post implements BotCommand {
    private final PostCommand postCommand;

    @Override
    public void execute(Update update) {
        postCommand.execute(update);

    }

    @Override
    public String getCommandName() {
        return "📰 Postlar";
    }
}
