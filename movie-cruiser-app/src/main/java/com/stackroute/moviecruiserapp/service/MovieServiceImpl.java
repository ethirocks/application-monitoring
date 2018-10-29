package com.stackroute.moviecruiserapp.service;

import com.stackroute.moviecruiserapp.domain.Movie;
import com.stackroute.moviecruiserapp.exceptions.MovieAlreadyExistsException;
import com.stackroute.moviecruiserapp.exceptions.MovieNotFoundException;
import com.stackroute.moviecruiserapp.repository.MovieRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Qualifier("impl1")
@Primary
public class MovieServiceImpl implements MovieService{

    MovieRepository movieRepository;
    private static final Logger LOGGER = LoggerFactory.getLogger(MovieServiceImpl.class);

    public MovieServiceImpl(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public List<Movie> getAllMovies() {
        return (List<Movie>) movieRepository.findAll();
    }


    public Optional<Movie> getMovieById(int id)  {
        Optional<Movie> movie;
        movie=movieRepository.findById(id);
        return movie;
    }

    public Movie getMovieByTitle(String title) throws MovieNotFoundException {
//        Optional<Movie> movie;
//        List<Movie> movieList=getAllMovies();
//        for(Movie movie2:movieList){
//            if(movie2.getTitle().equals(title))
//                return movie2;
//        }
        Movie savedMovie= movieRepository.getMovieByTitle(title);
        if(savedMovie == null){
            LOGGER.warn("Movie not found exception - {}", title);
            throw new MovieNotFoundException(title);
        }
        return savedMovie;
       // throw new MovieNotFoundException(title);
    }



    public List<Movie> deleteMovie(int id) throws MovieNotFoundException {
        if(!movieRepository.existsById(id))
            throw new MovieNotFoundException(id);
        Optional<Movie> movie1=getMovieById(id);
        movieRepository.delete(movie1.get());
        return getAllMovies();
    }


    public Movie updateMovie(int id,String comments) throws MovieNotFoundException {

        if(!movieRepository.existsById(id))
            throw new MovieNotFoundException(id);
        Optional<Movie> movie1=getMovieById(id);
        movie1.get().setComments(comments);
        Movie savedMovie=movieRepository.save(movie1.get());
        return savedMovie;
    }


    public Movie addMovie(Movie movie) throws MovieAlreadyExistsException {
        if(movieRepository.existsById(movie.getId()))
            throw new MovieAlreadyExistsException(movie);
//        for(Movie movie2:movieList){
//            if(movie2.getId()==(movie.getId()))
//                throw new MovieAlreadyExistsException(movie);
//        }
        Movie savedMovie=movieRepository.save(movie);
        if(savedMovie == null){
            LOGGER.warn("Movie already exists exception ");
            throw new MovieAlreadyExistsException(movie);
        }
        return savedMovie;
    }
}
