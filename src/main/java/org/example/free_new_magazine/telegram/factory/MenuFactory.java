package org.example.free_new_magazine.telegram.factory;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.List;

@Component
public class MenuFactory {

    public ReplyKeyboardMarkup mainMenu(){
        ReplyKeyboardMarkup kb = new ReplyKeyboardMarkup();
        kb.setResizeKeyboard(true);
        kb.setSelective(true);

        KeyboardRow row1 = new KeyboardRow();
        row1.add(new KeyboardButton("📰 Postlar"));
        row1.add(new KeyboardButton("📂 Kategoriyalar"));

        KeyboardRow row2 = new KeyboardRow();
        row2.add(new KeyboardButton("🔔 Obuna"));
        row2.add(new KeyboardButton("ℹ️ Yordam"));

        KeyboardRow row3 = new KeyboardRow();
        row3.add(new KeyboardButton("📣 Broadcast"));
        row3.add(new KeyboardButton("📊 Stat"));

        List<KeyboardRow> rows = List.of(row1, row2,row3);
        kb.setKeyboard(rows);
        return kb;
    }
}
