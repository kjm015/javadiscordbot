package io.kjm015.javadiscordbot.util;

import io.kjm015.javadiscordbot.listeners.MessageListener;
import io.kjm015.javadiscordbot.listeners.ReadyListener;
import io.kjm015.javadiscordbot.listeners.SlashCommandListener;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.requests.restaction.CommandListUpdateAction;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.Collections;

import static net.dv8tion.jda.api.interactions.commands.OptionType.STRING;

@Component
public class JdaUtil {

    @Bean
    public JDA jda() {
        String token = System.getenv("DISCORD_TOKEN");

        var jda = JDABuilder
                .createDefault(token)
                .enableIntents(GatewayIntent.MESSAGE_CONTENT)
                .addEventListeners(
                        new SlashCommandListener(),
                        new ReadyListener(),
                        new MessageListener()
                )
                .build();

        // Register your commands to make them visible globally on Discord:
        CommandListUpdateAction commands = jda.updateCommands();

        // Add all your commands on this action instance
        commands.addCommands(
                Commands.slash("say", "Makes the bot say what you tell it to")
                        .addOption(STRING, "content", "What the bot should say", true) // Accepting a user input
        );

        // Then finally send your commands to discord using the API
        commands.queue();

        return jda;
    }

}



