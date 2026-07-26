package org.example.free_new_magazine.telegram.factory;

import org.example.free_new_magazine.entity.Category;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class KeyboardFactory {


    public static final String MENU_PREFIX = "menu:";
    public static final String CATEGORY_PREFIX = "category:";
    public static final String YES_SUFFIX = ":yes";
    public static final String NO_SUFFIX = ":no";


    private static final int MAX_CODE_LEN = 40;

    public InlineKeyboardMarkup mainMenu(boolean isAdmin) {
        InlineKeyboardButton categories = btn("📚 Categories", MENU_PREFIX + "categories");
        InlineKeyboardButton latest = btn("🆕 Latest", MENU_PREFIX + "latest");
        InlineKeyboardButton off = btn("🔕 Off", MENU_PREFIX + "off");

        List<List<InlineKeyboardButton>> rows = new ArrayList<>();
        rows.add(List.of(categories, latest));
        rows.add(List.of(off));

        if (isAdmin) {
            InlineKeyboardButton broadcast = btn("📢 Broadcast", MENU_PREFIX + "admin:broadcast");
            InlineKeyboardButton stats = btn("📊 Stats", MENU_PREFIX + "admin:stats");
            rows.add(List.of(broadcast, stats));
        }

        return InlineKeyboardMarkup.builder()
                .keyboard(rows)
                .build();
    }


    public InlineKeyboardMarkup categoryKeyboard(List<Category> categories) {
        Objects.requireNonNull(categories, "categories cannot be null");

        List<List<InlineKeyboardButton>> rows = new ArrayList<>();

        for (int i = 0; i < categories.size(); i += 2) {
            List<InlineKeyboardButton> row = new ArrayList<>(2);
            row.add(categoryBtn(categories.get(i)));

            if (i + 1 < categories.size()) {
                row.add(categoryBtn(categories.get(i + 1)));
            }

            rows.add(row);
        }


        return InlineKeyboardMarkup.builder()
                .keyboard(rows)
                .build();
    }


    public InlineKeyboardMarkup confirmationKeyboard(String action) {
        if (action == null || action.isBlank()) {
            throw new IllegalArgumentException("action must not be null/blank");
        }

        InlineKeyboardButton yes = btn("✅ Ha", action + YES_SUFFIX);
        InlineKeyboardButton no = btn("❌ Yo'q", action + NO_SUFFIX);

        return InlineKeyboardMarkup.builder()
                .keyboard(List.of(List.of(yes, no)))
                .build();
    }
    
    private InlineKeyboardButton categoryBtn(Category cat) {
        if (cat == null) throw new IllegalArgumentException("Category cannot be null");
        if (cat.getCode() == null || cat.getCode().isBlank()) {
            throw new IllegalArgumentException("Category code cannot be null/blank");
        }

        String code = cat.getCode().trim();
        if (code.length() > MAX_CODE_LEN) code = code.substring(0, MAX_CODE_LEN);

        String text = (cat.getName() == null || cat.getName().isBlank())
                ? code
                : cat.getName();

        return btn(text, CATEGORY_PREFIX + code);
    }

    private InlineKeyboardButton btn(String text, String callbackData) {
        if (text == null || text.isBlank()) {
            throw new IllegalArgumentException("Button text must not be null/blank");
        }
        if (callbackData == null || callbackData.isBlank()) {
            throw new IllegalArgumentException("callbackData must not be null/blank");
        }

        return InlineKeyboardButton.builder()
                .text(text)
                .callbackData(callbackData)
                .build();
    }
}
