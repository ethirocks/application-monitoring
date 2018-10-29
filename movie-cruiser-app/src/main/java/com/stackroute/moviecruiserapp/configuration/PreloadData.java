package com.stackroute.moviecruiserapp.configuration;

import com.stackroute.moviecruiserapp.domain.Movie;
import com.stackroute.moviecruiserapp.repository.MovieRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;

@Configuration
public class PreloadData implements ApplicationListener<ContextRefreshedEvent>, CommandLineRunner {
    private MovieRepository movieRepository;

    public PreloadData(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        movieRepository.save(new Movie(3,"venom","english","save"));
        movieRepository.save(new Movie(4,"stree","hindi","save"));
    }
    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        movieRepository.save(new Movie(1,"la la","english","save"));
        movieRepository.save(new Movie(2,"devadas","telugu","save"));

    }
}
