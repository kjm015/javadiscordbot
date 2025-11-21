package io.kjm015.javadiscordbot.listeners;

import net.dv8tion.jda.api.entities.channel.ChannelType;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MessageListener extends ListenerAdapter {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {

        if (event.getAuthor().isBot()) return;

        var message = event.getMessage();
        var content = message.getContentRaw();

        if (content.equalsIgnoreCase("ping")) {
            var channel = event.getChannel();
            channel.sendMessage("pong")
                    .queue(); // Important to call .queue() on the RestAction returned by
            // sendMessage(...)
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
                    event.getMember().getEffectiveName(),
                    event.getMessage().getContentDisplay());
        }
    }
}
