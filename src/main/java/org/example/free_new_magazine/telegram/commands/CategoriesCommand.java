package org.example.free_new_magazine.telegram.commands;

import lombok.RequiredArgsConstructor;
import org.example.free_new_magazine.entity.Category;
import org.example.free_new_magazine.repository.CategoryRepository;
import org.example.free_new_magazine.telegram.factory.TelegramMessageFactory;
import org.example.free_new_magazine.telegram.service.TelegramSenderService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import java.util.List;

@Component
@RequiredArgsConstructor
public class CategoriesCommand implements BotCommand {

    private final CategoryRepository categoryRepository;
    private final TelegramSenderService senderService;
    private final TelegramMessageFactory messageFactory;


    @Override
    public void execute(Update update) {
        Long chatId = update.getMessage().getChatId();

        List<Category> categories = categoryRepository.findAll();
        if (categories.isEmpty()) {
            senderService.send(chatId, "Hozircha kategoriyalar mavjud emas 📂");
            return;
        }
        InlineKeyboardMarkup markup = messageFactory.createCategoryKeyboard(categories);
        senderService.sendWithKeyboard(chatId, "📂 <b>Kategoriyalardan birini tanlang:</b>", markup);
    }

    @Override
    public String getCommandName() {
        return "/categories";
    }
}