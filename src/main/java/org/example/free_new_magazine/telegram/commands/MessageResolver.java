package org.example.free_new_magazine.telegram.commands;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
@RequiredArgsConstructor
public class MessageResolver {

    private final MessageSource  messageSource;

    public String get(String key, String lang){
        return messageSource.getMessage(
                key,
                null,
                new Locale(lang==null ? "uz" : lang)
        );
    }
}

