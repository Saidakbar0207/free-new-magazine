package org.example.free_new_magazine.telegram.commands;

import lombok.RequiredArgsConstructor;
import org.example.free_new_magazine.dto.TelegramPostDTO;
import org.example.free_new_magazine.service.PostService;
import org.example.free_new_magazine.telegram.service.TelegramSenderService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

@Component
@RequiredArgsConstructor
public class PostCommand implements BotCommand {

    private final TelegramSenderService senderService;
    private final PostService postService;

    @Override
    public void execute(Update update) {
        Long chatId = update.getMessage().getChatId();

        List<TelegramPostDTO> posts = postService.getLatestForTelegram(5);

        if (posts.isEmpty()) {
            senderService.send(chatId, "Hozircha post yo‘q.");
            return;
        }

        StringBuilder sb = new StringBuilder("📰 <b>So‘nggi postlar</b>\n\n");
        for (TelegramPostDTO p : posts) {
            sb.append("• <b>").append(escape(p.title())).append("</b>\n");
            if (p.content() != null && !p.content().isBlank()) {
                sb.append(escape(p.content())).append("\n");
            }
            if (p.url() != null && !p.url().isBlank()) {
                sb.append("🔗 ").append(escape(p.url())).append("\n");
            }
            sb.append("\n");
        }

        senderService.send(chatId, sb.toString());
    }

    @Override
    public String getCommandName() {
        return "/post";
    }

    private String escape(String s) {
        if (s == null) return "";
        return s.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;");
    }
}
