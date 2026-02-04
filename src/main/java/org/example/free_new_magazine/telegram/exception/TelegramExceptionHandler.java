package org.example.free_new_magazine.telegram.exception;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.free_new_magazine.telegram.service.TelegramSenderService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
@Slf4j
@RequiredArgsConstructor
public class TelegramExceptionHandler {

    private final TelegramSenderService senderService;


    public void handle(Exception e, Long chatId) {
        log.error("Telegram Error for chatId {}: ", chatId, e);

        if (e instanceof TelegramApiException) {
            handleTelegramError(chatId);
        } else if (e instanceof NullPointerException) {
            senderService.send(chatId, "⚠️ Ma'lumot topilmadi. Iltimos qaytadan urinib ko'ring.");
        } else {
            senderService.send(chatId, "❌ Kechirasiz, ichki tizimda xatolik yuz berdi. Tez orada tuzatamiz!");
        }
    }

    private void handleTelegramError(Long chatId) {
        log.warn("Telegram API xatosi chatId: {}", chatId);
    }
}