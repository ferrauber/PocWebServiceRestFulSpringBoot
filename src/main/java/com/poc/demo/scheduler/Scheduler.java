package com.poc.demo.scheduler;

import com.poc.demo.service.MovieService;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class Scheduler {

    private final MovieService movieService;

    @Scheduled(cron = "${cron.import-movies}")
    public void runImportMovies() {
        movieService.importCSVMovieFile();
    }
}