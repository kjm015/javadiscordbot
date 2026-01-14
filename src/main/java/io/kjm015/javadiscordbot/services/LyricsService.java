package io.kjm015.javadiscordbot.services;

import com.jagrosh.jlyrics.Lyrics;
import com.jagrosh.jlyrics.LyricsClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class LyricsService {

    private final LyricsClient lyricsClient;

    public LyricsService() {
        lyricsClient = new LyricsClient();
    }

    public Lyrics getLyrics(String query) {
        log.info("Getting lyrics for {}", query);
        var result = lyricsClient.getLyrics(query);

        Lyrics lyrics = null;

        try {
            lyrics = result.get();
        } catch (Exception e) {
            log.error("Error getting lyrics for {}", query, e);
        }

        return lyrics;
    }
}
