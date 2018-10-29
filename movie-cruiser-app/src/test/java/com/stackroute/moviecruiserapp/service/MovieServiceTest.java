package com.stackroute.moviecruiserapp.service;

import com.stackroute.moviecruiserapp.domain.Movie;
import com.stackroute.moviecruiserapp.exceptions.MovieAlreadyExistsException;
import com.stackroute.moviecruiserapp.exceptions.MovieNotFoundException;
import com.stackroute.moviecruiserapp.repository.MovieRepository;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class MovieServiceTest {
    Movie movie;

    //Create a mock for UserRepository
    @Mock//test double
    MovieRepository movieRepository;

    //Inject the mocks as dependencies into UserServiceImpl
    @InjectMocks
    MovieServiceImpl movieService;



    List<Movie> list= null;


    @Before
    public void setUp(){
        //Initialising the mock object
        MockitoAnnotations.initMocks(this);
        movie = new Movie();
        movie.setId(120);
        movie.setTitle("Rang De Basanti");
        movie.setComments("good movie");
        movie.setLanguage("Hindi");
        list = new ArrayList<>();
        list.add(movie);
    }

    @After
    public void tearDown() throws Exception {
        movieRepository.deleteAll();
    }

    @Test
    public void saveMovieTestSuccess() throws MovieAlreadyExistsException {
        when(movieRepository.save((Movie)any())).thenReturn(movie);
        Movie savedMovie = movieService.addMovie(movie);
        Assert.assertEquals(movie,savedMovie);

        //verify here verifies that userRepository save method is only called once
        verify(movieRepository,times(1)).save(movie);

    }

    @Test(expected = MovieAlreadyExistsException.class)
    public void saveMovieTestFailure() throws MovieAlreadyExistsException {
        when(movieRepository.save((Movie)any())).thenReturn(null);
        Movie savedMovie = movieService.addMovie(movie);
        System.out.println("savedUser" + savedMovie);
        Assert.assertEquals(movie,savedMovie);
//add verify
        verify(movieService,times(1)).addMovie(movie);
        verify(movieRepository,times(1)).save(movie);
       //userService.saveUser(user);


    }

    @Test
    public void getAllMovies(){

        movieRepository.save(movie);
        //stubbing the mock to return specific data
        when(movieRepository.findAll()).thenReturn(list);
        List<Movie> movielist = movieService.getAllMovies();
        Assert.assertEquals(list,movielist);
        verify(movieRepository,times(1)).findAll();
        //add verify
    }

    @Test
    public void deleteMovie() throws MovieNotFoundException {
        when(movieRepository.existsById(movie.getId())).thenReturn(true);
        when(movieService.getMovieById(movie.getId())).thenReturn(java.util.Optional.ofNullable(movie));
        List<Movie> movielist = movieService.deleteMovie(movie.getId());
        List<Movie> list=movieRepository.findAll();
        Assert.assertEquals(list,movielist);
        verify(movieRepository,times(1)).existsById(movie.getId());
        verify(movieRepository,times(1)).delete(movie);
    }
    @Test(expected = MovieNotFoundException.class)
    public void deleteMovieFailure() throws MovieNotFoundException {
        when(movieRepository.existsById(movie.getId())).thenReturn(false);
        List<Movie> movielist = movieService.deleteMovie(movie.getId());
    }
    @Test
    public void updateMovie() throws MovieNotFoundException {
        when(movieRepository.save((Movie)any())).thenReturn(movie);
        when(movieRepository.existsById(movie.getId())).thenReturn(true);
        when(movieService.getMovieById(movie.getId())).thenReturn(java.util.Optional.ofNullable(movie));
        Movie updatedMovie = movieService.updateMovie(120,"flop");
        Assert.assertEquals(movie.getComments(),updatedMovie.getComments());
        verify(movieRepository,times(1)).existsById(movie.getId());
        verify(movieRepository,times(1)).save(movie);
    }
    @Test(expected = MovieNotFoundException.class)
    public void updateMovieFailure() throws MovieNotFoundException {
        when(movieRepository.existsById(movie.getId())).thenReturn(false);
        Movie updatedMovie = movieService.updateMovie(120,"flop");
    }
    @Test
    public void searchMovie() throws MovieNotFoundException {
        when(movieRepository.getMovieByTitle(anyString())).thenReturn(movie);
        Movie updatedMovie = movieService.getMovieByTitle("Rang De Basanti");
        Assert.assertSame(movie,updatedMovie);
        verify(movieRepository,times(1)).getMovieByTitle(anyString());
    }
    @Test(expected = MovieNotFoundException.class)
    public void searchMovieFailure() throws MovieNotFoundException {
        when(movieRepository.getMovieByTitle(anyString())).thenReturn(null);
        Movie updatedMovie = movieService.getMovieByTitle("sdc");
        Assert.assertSame(null,updatedMovie);
        verify(movieRepository,times(1)).getMovieByTitle(anyString());
    }
}
