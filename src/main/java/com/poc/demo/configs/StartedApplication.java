package com.poc.demo.configs;

import com.poc.demo.service.MovieService;
import lombok.AllArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;

@AllArgsConstructor
@Configuration
public class StartedApplication {

    private final MovieService movieService;

    @EventListener(ApplicationReadyEvent.class)
    public void runImportMovies() {
        movieService.importCSVMovieFile();
    }
}