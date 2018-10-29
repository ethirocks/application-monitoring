package com.stackroute.moviecruiserapp.repository;

import com.stackroute.moviecruiserapp.domain.Movie;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;



@Repository
public interface MovieRepository extends MongoRepository<Movie,Integer> {
    @Query("{ 'title' : ?0 }")
    public Movie getMovieByTitle(@Param("title") String title);
}
