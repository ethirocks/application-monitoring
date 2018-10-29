package com.stackroute.moviecruiserapp.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


public class MovieNotFoundException extends Exception {
    public MovieNotFoundException(String title) {
        super(title+" Movie not found");
    }
    public MovieNotFoundException(int id) {
        super(id+" Movie not found");
    }
}
