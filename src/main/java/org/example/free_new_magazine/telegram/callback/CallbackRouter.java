package org.example.free_new_magazine.telegram.callback;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CallbackRouter {
    private final List<CallbackHandler> handlers;

    public void route(Update update) {
        String data  = update.getCallbackQuery().getData();
        handlers.stream()
                .filter(h -> h.supports(data))
                .findFirst()
                .orElseThrow(()-> new IllegalArgumentException("Unknown callback: " + data))
                .handle(update);

    }
}
