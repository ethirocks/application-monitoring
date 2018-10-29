package com.stackroute.moviecruiserapp.repository;

import com.stackroute.moviecruiserapp.domain.Movie;
import com.stackroute.moviecruiserapp.exceptions.MovieNotFoundException;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.List;
import java.util.Optional;

//This is integrated test we need DB server
//Find out what are testRunner
@RunWith(SpringRunner.class)
@DataMongoTest
public class MovieRepositoryTest {
    @Autowired
    MovieRepository movieRepository;
    Movie movie;

    @Before
    public void setUp()
    {
        movie = new Movie();
        movie.setId(10);
        movie.setTitle("Rang De Basanti");
        movie.setComments("good movie");
        movie.setLanguage("Hindi");

    }
    @After
    public void tearDown() throws Exception {
        movieRepository.deleteAll();
    }
    @Test
    public void testSaveMovie(){
        movieRepository.insert(movie);
        Movie fetchMovie = movieRepository.findById(movie.getId()).get();
        Assert.assertEquals(10,fetchMovie.getId());
    }
    @Test
    public void testSaveMovieFailure(){
        Movie testMovie = new Movie(101,"spiderman","English","4 star");
        movieRepository.insert(testMovie);
        movieRepository.insert(movie);
        Movie fetchMovie=movieRepository.findById(movie.getId()).get();
        Assert.assertNotSame(fetchMovie,movie);
    }
    @Test
    public void testDeleteMovie(){
        movieRepository.insert(movie);
        movieRepository.delete(movie);
        boolean fetchMovie = movieRepository.existsById(movie.getId());
        Assert.assertEquals(false,fetchMovie);
    }
    @Test
    public void testDeleteMovieFailure(){
        Movie testMovie1 = new Movie(1,"dev1","telugu1","comment1");
        movieRepository.insert(testMovie1);
        Movie fetchMovie = movieRepository.findById(testMovie1.getId()).get();
        Assert.assertNotSame(testMovie1,fetchMovie);
    }
    @Test
    public void updateMovieTest() {
        movieRepository.insert(movie);
        Movie movie1 = movieRepository.findById(movie.getId()).get();
        movie1.setComments("Sending");
        movieRepository.save(movie1);
        Assert.assertEquals("Sending", movie1.getComments());
    }
    @Test
    public void updateMovieTestFailure() {
        movieRepository.insert(movie);
        Movie movie1 = movieRepository.findById(movie.getId()).get();
        movie1.setComments("Sending");
        movieRepository.save(movie1);
        Assert.assertNotSame(movie, movie1);
    }
    @Test
    public void testgetByMovieTitle(){
        movieRepository.insert(movie);
        Movie fetchMovie = movieRepository.getMovieByTitle(movie.getTitle());
        Assert.assertEquals(10,fetchMovie.getId());
    }
    @Test
    public void testgetByMovieTitleFailure(){
        Movie testMovie1 = new Movie(1,"dev1","telugu1","comment1");
        movieRepository.insert(testMovie1);
        Movie fetchMovie = movieRepository.getMovieByTitle(testMovie1.getTitle());
        Assert.assertNotSame(testMovie1,fetchMovie);
    }
    @Test
    public void testGetAllMovie(){
        List<Movie> listold = movieRepository.findAll();
        Movie movie1 = new Movie(10,"Gita Govindam","Telugu","3 star");
        Movie movie2 = new Movie(9,"Bommarillu","Telugu","4 star");
        movieRepository.insert(movie1);
        movieRepository.insert(movie2);
        List<Movie> listUpdated = movieRepository.findAll();
        Assert.assertEquals(listold.size()+2,listUpdated.size());
    }

}
