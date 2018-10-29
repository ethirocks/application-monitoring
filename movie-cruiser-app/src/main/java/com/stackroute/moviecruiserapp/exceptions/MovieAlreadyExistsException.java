package com.stackroute.moviecruiserapp.exceptions;

import com.stackroute.moviecruiserapp.domain.Movie;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


public class MovieAlreadyExistsException extends Exception {
    public MovieAlreadyExistsException(Movie movie) {
        super(movie.getTitle()+" Movie already exists");
    }
}
