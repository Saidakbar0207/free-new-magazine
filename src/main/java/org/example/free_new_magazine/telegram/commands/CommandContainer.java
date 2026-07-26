package org.example.free_new_magazine.telegram.commands;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class CommandContainer {

    private final Map<String, BotCommand> commands;
    private final BotCommand defaultCommand;

    public CommandContainer(List<BotCommand> commandList, DefaultCommandHandler defaultHandler){
        this.commands = commandList.stream()
                .collect(Collectors.toMap(BotCommand::getCommandName, cmd ->cmd));
        this.defaultCommand = defaultHandler;
    }

    public BotCommand retrieveCommand(String commandIdentifier){
        if (commandIdentifier == null) return defaultCommand;

        String text = commandIdentifier.trim();
        if (text.isEmpty()) return defaultCommand;


        BotCommand exact = commands.get(text);
        if (exact != null) return exact;

        if (text.startsWith("/")) {
            String key = text.split("\\s+")[0];
            return commands.getOrDefault(key, defaultCommand);
        }

        return defaultCommand;
    }



}

