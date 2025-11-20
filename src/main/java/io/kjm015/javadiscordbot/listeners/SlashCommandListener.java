package io.kjm015.javadiscordbot.listeners;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

public class SlashCommandListener extends ListenerAdapter {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {

        if (event.getName().equals("say")) {
            String content = event.getOption("content", OptionMapping::getAsString);
            log.info("Received command: /say {}", content);
            event.reply(Objects.requireNonNullElse(content, "You need to provide a message to say!")).queue();
        }

    }
}
