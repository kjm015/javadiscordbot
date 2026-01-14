package io.kjm015.javadiscordbot.listeners;

import io.kjm015.javadiscordbot.services.CommandsEventService;
import io.kjm015.javadiscordbot.services.LyricsService;
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
    private final LyricsService lyricsService;

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {

        switch (event.getName()) {
            case "say" -> handleSayCommand(event);
            case "stats" -> handleStatsCommand(event);
            case "ping" -> handlePingCommand(event);
            case "lyrics" -> handleLyricsCommand(event);
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

    private void handlePingCommand(SlashCommandInteractionEvent event) {
        event.reply("pong").queue();
        commandsEventService.saveCommandEvent("ping", "", event.getUser().getName());
    }

    private void handleLyricsCommand(SlashCommandInteractionEvent event) {
        var content = event.getOption("song", OptionMapping::getAsString);
        log.info("Received command: /lyrics {}", content);

        var lyrics = lyricsService.getLyrics(content);

        if (lyrics != null) {
            event.reply(lyrics.getContent()).queue();
        } else {
            event.reply(String.format("I couldn't find any lyrics for \"%s\"!", content)).queue();
        }

        commandsEventService.saveCommandEvent("lyrics", content, event.getUser().getName());
    }
}
