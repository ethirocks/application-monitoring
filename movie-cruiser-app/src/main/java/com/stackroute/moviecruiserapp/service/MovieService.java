package com.stackroute.moviecruiserapp.service;

import com.stackroute.moviecruiserapp.domain.Movie;
import com.stackroute.moviecruiserapp.exceptions.MovieAlreadyExistsException;
import com.stackroute.moviecruiserapp.exceptions.MovieNotFoundException;


import java.util.List;
import java.util.Optional;

public interface MovieService {
    //@Query(value="select * from Movie",nativeQuery = true)
    public List<Movie> getAllMovies();
    //@Query(value="select * from Movie o where o.id=?1",nativeQuery = true)
    public Optional<Movie> getMovieById(int id) throws MovieNotFoundException;
    //@Query(value="select o from Movie o where o.title = :title",nativeQuery = true)
    public Movie getMovieByTitle(String title) throws MovieNotFoundException;
    //@Query(value="delete from Movie o where o=?1",nativeQuery = true)
    public List<Movie> deleteMovie(int id) throws MovieNotFoundException;
    public Movie updateMovie(int id,String comments) throws MovieNotFoundException;
    public Movie addMovie(Movie movie) throws MovieAlreadyExistsException;
}
