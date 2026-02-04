package org.example.free_new_magazine.telegram.commands.menu_commands;

import lombok.RequiredArgsConstructor;
import org.example.free_new_magazine.telegram.commands.BotCommand;
import org.example.free_new_magazine.telegram.commands.OffCommand;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@RequiredArgsConstructor
public class Off  implements BotCommand {
    private final OffCommand offCommand;

    @Override
    public void execute(Update update) {
        offCommand.execute(update);

    }

    @Override
    public String getCommandName() {
        return "";
    }
}
