package org.example.free_new_magazine.telegram.factory;

import org.example.free_new_magazine.entity.Category;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

@Component
public class TelegramMessageFactory {

    public InlineKeyboardMarkup createCategoryKeyboard(List<Category> categories) {
        List<List<InlineKeyboardButton>> rows = new ArrayList<>();

        for (Category cat : categories) {
            var button = InlineKeyboardButton.builder()
                    .text(cat.getName())
                    .callbackData("category:" + cat.getCode())
                    .build();
            rows.add(List.of(button));
        }

        return new InlineKeyboardMarkup(rows);
    }

    public InlineKeyboardMarkup createConfirmationKeyboard(String action) {
        var yes = InlineKeyboardButton.builder().text("✅ Ha").callbackData(action + ":yes").build();
        var no = InlineKeyboardButton.builder().text("❌ Yo'q").callbackData(action + ":no").build();

        return new InlineKeyboardMarkup(List.of(List.of(yes, no)));
    }
}