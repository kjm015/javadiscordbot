package io.kjm015.javadiscordbot.listeners;

import io.kjm015.javadiscordbot.services.CommandsEventService;
import java.util.Objects;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SlashCommandListener extends ListenerAdapter {

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private final CommandsEventService commandsEventService;

    @Autowired
    private SlashCommandListener(CommandsEventService commandsEventService) {
        this.commandsEventService = commandsEventService;
    }

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {

        if (event.getName().equals("say")) {
            var content = event.getOption("content", OptionMapping::getAsString);
            log.info("Received command: /say {}", content);
            event.reply(
                            Objects.requireNonNullElse(
                                    content, "You need to provide a message to say!"))
                    .queue();

            commandsEventService.saveCommandEvent("say", content);
        }
    }
}
