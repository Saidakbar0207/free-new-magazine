package org.example.free_new_magazine.telegram.callback;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.free_new_magazine.telegram.service.TelegramCategoryService;
import org.example.free_new_magazine.telegram.service.TelegramSenderService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@RequiredArgsConstructor
@Slf4j
public class CategoryCallbackHandler implements CallbackHandler {

    private final TelegramCategoryService categoryService;
    private final TelegramSenderService senderService;

    @Override
    public boolean supports(String data) {
        return data.startsWith("category:");
    }

    @Override
    public void handle(Update update) {
        CallbackQuery callbackQuery = update.getCallbackQuery();
        String data = callbackQuery.getData();
        Long chatId = callbackQuery.getMessage().getChatId();


        String code = data.substring("category:".length());

        try {
            categoryService.subscribe(chatId, code);
            senderService.send(chatId, "✅ Siz muvaffaqiyatli obuna bo'ldingiz: " + code);
        } catch (Exception e) {
            log.error("Obunada xato: ", e);
            senderService.send(chatId, "❌ Obuna bo'lishda xatolik yuz berdi.");
        }
    }

    @Override
    public String getCallbackCode() {
        return "category:";
    }
}