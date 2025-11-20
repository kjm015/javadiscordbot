package io.kjm015.javadiscordbot.listeners;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;

import java.util.Objects;

public class SlashCommandListener extends ListenerAdapter {
    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {

        if (event.getName().equals("say")) {
            String content = event.getOption("content", OptionMapping::getAsString);
            event.reply(Objects.requireNonNullElse(content, "You need to provide a message to say!")).queue();
        }

    }
}
