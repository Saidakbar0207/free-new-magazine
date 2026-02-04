package org.example.free_new_magazine.telegram.commands;

import lombok.RequiredArgsConstructor;
import org.example.free_new_magazine.entity.TelegramUser;
import org.example.free_new_magazine.repository.TelegramUserRepository;
import org.example.free_new_magazine.telegram.config.TelegramBotConfig;
import org.example.free_new_magazine.telegram.service.TelegramSenderService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

@Component
@RequiredArgsConstructor
public class AdminBroadcastCommand implements BotCommand {
    private final TelegramBotConfig botConfig;
    private final TelegramSenderService senderService;
    private final TelegramUserRepository repository;

    @Override
    public void execute(Update update) {
        Long chatId = update.getMessage().getChatId();

        if(!botConfig.isAdmin(chatId)){
            senderService.send(chatId,"⛔ Siz admin emassiz");
            return;
        }
        String full = update.getMessage().getText();
        String msg = full.replaceFirst("^/broadcast\\s*","").trim();

        if(msg.isEmpty()){
            senderService.send(chatId,"✍\uFE0F Misol: /broadcast Salom hammaga!");
            return;
        }

        List<TelegramUser> users = repository.findAllByActiveTrue();

        int ok = 0;

        for (TelegramUser user : users) {
            try{
                senderService.send(user.getChatId(),msg);
                ok++;
                Thread.sleep(35);
            } catch (Exception e){
                e.printStackTrace();
            }
        }
        senderService.send(chatId, "✅ Yuborildi: " + ok + " ta userga");
    }

    @Override
    public String getCommandName() {
        return "/broadcast";
    }
}
