package io.kjm015.javadiscordbot.listeners;

import io.kjm015.javadiscordbot.services.CommandsEventService;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class SlashCommandListener extends ListenerAdapter {

    private final CommandsEventService commandsEventService;

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {

        if (event.getName().equals("say")) {
            handleSayCommand(event);
        } else if (event.getName().equals("stats")) {
            handleStatsCommand(event);
        } else if (event.getName().equals("ping")) {
            event.reply("pong").queue();
        }
    }

    private void handleSayCommand(SlashCommandInteractionEvent event) {
        var content = event.getOption("content", OptionMapping::getAsString);
        log.info("Received command: /say {}", content);
        event.reply(Objects.requireNonNullElse(content, "You need to provide a message to say!"))
                .queue();

        commandsEventService.saveCommandEvent("say", content, event.getUser().getName());
    }

    private void handleStatsCommand(SlashCommandInteractionEvent event) {
        log.info("Received command: /stats");
        var events = commandsEventService.getAllCommandEvents();

        var eventCount = events.size();
        var reply = String.format("%d commands have been executed!", eventCount);
        event.reply(reply).queue();

        commandsEventService.saveCommandEvent("stats", "", event.getUser().getName());
    }
}
