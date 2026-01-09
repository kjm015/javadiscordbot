package io.kjm015.javadiscordbot.services;

import static org.springframework.http.MediaType.APPLICATION_JSON;

import io.kjm015.javadiscordbot.conifg.CommandsSettings;
import io.kjm015.javadiscordbot.models.CommandEventRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class CommandsEventService {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private final RestClient client;
    private final CommandsSettings settings;

    @Autowired
    public CommandsEventService(CommandsSettings settings) {
        this.client = RestClient.create();
        this.settings = settings;
    }

    public void saveCommandEvent(String name, String content) {
        log.info("Sending request to save command event: {} {}", name, content);

        var request = new CommandEventRequest(name, content);
        client.post()
                .uri(settings.getUrl() + "/save")
                .contentType(APPLICATION_JSON)
                .body(request)
                .retrieve()
                .toBodilessEntity();
    }
}
