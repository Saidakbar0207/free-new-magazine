package org.example.free_new_magazine.telegram.commands.menu_commands;

import lombok.RequiredArgsConstructor;
import org.example.free_new_magazine.telegram.commands.BotCommand;
import org.example.free_new_magazine.telegram.commands.CategoriesCommand;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@RequiredArgsConstructor
public class Categories implements BotCommand {

    private final CategoriesCommand categoriesCommand;

    @Override
    public String getCommandName() {
        return "📂 Kategoriyalar";
    }

    @Override
    public void execute(Update update) {
        categoriesCommand.execute(update);
    }
}
