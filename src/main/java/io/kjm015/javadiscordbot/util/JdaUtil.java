package io.kjm015.javadiscordbot.util;

import static net.dv8tion.jda.api.interactions.commands.OptionType.STRING;

import io.kjm015.javadiscordbot.listeners.MessageListener;
import io.kjm015.javadiscordbot.listeners.ReadyListener;
import io.kjm015.javadiscordbot.listeners.SlashCommandListener;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class JdaUtil {

    private final SlashCommandListener slashCommandListener;

    @Autowired
    public JdaUtil(SlashCommandListener slashCommandListener) {
        this.slashCommandListener = slashCommandListener;
    }

    @Bean
    public JDA jda() {
        var token = System.getenv("DISCORD_TOKEN");
        var jda =
                JDABuilder.createDefault(token)
                        .enableIntents(GatewayIntent.MESSAGE_CONTENT)
                        .addEventListeners(
                                slashCommandListener, new ReadyListener(), new MessageListener())
                        .build();

        // Register your commands to make them visible globally on Discord:
        var commands =
                jda.updateCommands()
                        .addCommands(
                                Commands.slash("say", "Makes the bot say what you tell it to")
                                        .addOption(
                                                STRING,
                                                "content",
                                                "What the bot should say",
                                                true));

        // Then finally send your commands to discord using the API
        commands.queue();

        return jda;
    }
}
