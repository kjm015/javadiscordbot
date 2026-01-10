package io.kjm015.javadiscordbot.listeners;

import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.entities.channel.ChannelType;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

@Slf4j
public class MessageListener extends ListenerAdapter {

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {

        if (event.getAuthor().isBot()) return;

        var message = event.getMessage();
        var content = message.getContentRaw();

        if (content.equalsIgnoreCase("ping")) {
            var channel = event.getChannel();
            channel.sendMessage("pong").queue();
        }

        if (event.isFromType(ChannelType.PRIVATE)) {
            log.info(
                    "[PM] {}: {}",
                    event.getAuthor().getName(),
                    event.getMessage().getContentDisplay());
        } else {
            log.info(
                    "[{}][{}] {}: {}",
                    event.getGuild().getName(),
                    event.getChannel().getName(),
                    Objects.requireNonNull(event.getMember()).getEffectiveName(),
                    event.getMessage().getContentDisplay());
        }
    }
}
