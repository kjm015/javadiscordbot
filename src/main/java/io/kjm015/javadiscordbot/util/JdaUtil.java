package io.kjm015.javadiscordbot.util;

import static net.dv8tion.jda.api.interactions.commands.OptionType.STRING;

import io.kjm015.javadiscordbot.listeners.MessageListener;
import io.kjm015.javadiscordbot.listeners.ReadyListener;
import io.kjm015.javadiscordbot.listeners.SlashCommandListener;
import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JdaUtil {

    private final SlashCommandListener slashCommandListener;

    @Bean
    public JDA jda() {
        // Set up your Discord app token as an environment variable
        var token = System.getenv("DISCORD_TOKEN");
        var jda =
                JDABuilder.createDefault(token)
                        .enableIntents(GatewayIntent.MESSAGE_CONTENT)
                        .addEventListeners(
                                slashCommandListener, new ReadyListener(), new MessageListener())
                        .build();

        // Restart your Discord client to get these to show up in the command list
        // Register your commands to make them visible globally on Discord:
        var commands =
                jda.updateCommands()
                        .addCommands(
                                Commands.slash("lyrics", "Gets lyrics for a song!")
                                        .setName("lyrics")
                                        .addOption(
                                                STRING,
                                                "song",
                                                "The song that you want lyrics for",
                                                true),
                                Commands.slash("ping", "Replies with Pong!").setName("ping"),
                                Commands.slash("say", "Makes the bot say what you tell it to")
                                        .setName("say")
                                        .addOption(
                                                STRING, "content", "What the bot should say", true),
                                Commands.slash("stats", "Display stats about executed bot commands")
                                        .setName("stats"));

        // Then finally send your commands to discord using the API
        commands.queue();

        return jda;
    }
}
