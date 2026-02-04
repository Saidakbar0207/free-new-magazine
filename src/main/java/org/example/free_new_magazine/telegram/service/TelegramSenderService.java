package org.example.free_new_magazine.telegram.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.free_new_magazine.telegram.bot.FreeNewsTelegramBot;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Service
@Slf4j
public class TelegramSenderService {

    private final FreeNewsTelegramBot bot;

    public TelegramSenderService(@Lazy FreeNewsTelegramBot bot) {
        this.bot = bot;
    }


    public void send(Long chatId, String text) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(text);
        message.setParseMode("HTML");
        message.setDisableWebPagePreview(false);

        executeMessage(message);
    }


    public void sendWithKeyboard(Long chatId, String text, InlineKeyboardMarkup markup) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId.toString());
        message.setText(text);
        message.setParseMode("HTML");
        message.setReplyMarkup(markup);

        executeMessage(message);
    }

    public void sendWithReplyKeyboard(Long chatId, String text, ReplyKeyboard keyboard) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId.toString());
        message.setText(text);
        message.setParseMode("HTML");
        message.setReplyMarkup(keyboard);

        executeMessage(message);
    }

    public void editMessage(Long chatId, String text, Integer messageId ,InlineKeyboardMarkup markup) {
        EditMessageText editMessageText = new EditMessageText();
        editMessageText.setChatId(chatId.toString());
        editMessageText.setMessageId(messageId);
        editMessageText.setText(text);
        editMessageText.setParseMode("HTML");

        if(markup != null) {
            editMessageText.setReplyMarkup(markup);
        }

        try{
            bot.execute(editMessageText);
        }catch (TelegramApiException e){
            log.error("Edit message error: ",e);
        }
    }
    public void answerCallback(String callbackQueryId) {
        AnswerCallbackQuery ans = new AnswerCallbackQuery();
        ans.setCallbackQueryId(callbackQueryId);

        try{
            bot.execute(ans);
        }catch (TelegramApiException e){
            log.error("Callback error: ",e);
        }
    }



    private void executeMessage(SendMessage message) {
        try {
            log.debug("Telegram send => chatId={}, text={}", message.getChatId(), message.getText());
            bot.execute(message);
        } catch (TelegramApiException e) {
            log.error("Telegram xabari yuborishda xato: ", message.getText(),e);


        }
    }
}