package org.example.free_new_magazine.telegram.commands;


import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public interface BotCommand {

    void execute(Update update);
    String getCommandName();
}
