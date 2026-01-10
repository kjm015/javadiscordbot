package io.kjm015.javadiscordbot.services;

import static org.springframework.http.MediaType.APPLICATION_JSON;

import io.kjm015.javadiscordbot.conifg.CommandsSettings;
import io.kjm015.javadiscordbot.models.CommandEvent;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Slf4j
@Service
public class CommandsEventService {

    private final RestClient client;
    private final CommandsSettings settings;

    @Autowired
    public CommandsEventService(CommandsSettings settings) {
        this.client = RestClient.create();
        this.settings = settings;
    }

    public void saveCommandEvent(String name, String content, String sender) {
        log.info("Sending request to save command event: {} {}", name, content);

        var request = new CommandEvent(name, content, sender);

        try {
            client.post()
                    .uri(settings.getUrl() + "/save")
                    .contentType(APPLICATION_JSON)
                    .body(request)
                    .retrieve()
                    .toBodilessEntity();
        } catch (Exception e) {
            log.error("Error saving command event", e);
        }
    }

    public List<CommandEvent> getAllCommandEvents() {
        log.info("Sending request to get all command events from commands-api");

        List<CommandEvent> events = List.of();

        try {
            var response =
                    client.get()
                            .uri(settings.getUrl() + "/all")
                            .retrieve()
                            .toEntity(new ParameterizedTypeReference<List<CommandEvent>>() {})
                            .getBody();

            if (response != null) {
                events = response;
            }
        } catch (Exception e) {
            log.error("Error getting all command events from commands-api", e);
        }

        return events;
    }
}
