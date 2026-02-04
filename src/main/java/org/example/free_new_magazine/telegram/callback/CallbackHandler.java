package org.example.free_new_magazine.telegram.callback;

import org.telegram.telegrambots.meta.api.objects.Update;

public interface CallbackHandler {
    boolean supports(String data);
    void handle(Update update);
    String getCallbackCode();
}