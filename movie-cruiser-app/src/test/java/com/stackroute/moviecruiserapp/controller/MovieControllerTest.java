package com.stackroute.moviecruiserapp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stackroute.moviecruiserapp.domain.Movie;
import com.stackroute.moviecruiserapp.exceptions.MovieAlreadyExistsException;
import com.stackroute.moviecruiserapp.exceptions.MovieNotFoundException;
import com.stackroute.moviecruiserapp.service.MovieService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@WebMvcTest
public class MovieControllerTest {
    //find what is MockMvc
    @Autowired
    private MockMvc mockMvc;

    private Movie movie;

    @MockBean
    private MovieService movieService;
    @InjectMocks
    private MovieController movieController;

    private List<Movie> list =null;
    private List<Movie> resultList =null;

    @Before
    public void setUp(){

        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(movieController).build();
        movie = new Movie(12,"geethagovindam","telugu","avg");
        list = new ArrayList();
        list.add(movie);
        System.out.println(asJsonString(movie));
    }


    @Test
    public void saveMovie() throws Exception {
        when(movieService.addMovie(any())).thenReturn(movie);
        mockMvc.perform(MockMvcRequestBuilders.post("/movie-api/v1/movie")
                .contentType(MediaType.APPLICATION_JSON).content(asJsonString(movie)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andDo(MockMvcResultHandlers.print());
        verify(movieService,times(1)).addMovie(any());
    }
    @Test
    public void saveMovieFailure() throws Exception {
        when(movieService.addMovie(any())).thenThrow(MovieAlreadyExistsException.class);
        mockMvc.perform(MockMvcRequestBuilders.post("/movie-api/v1/movie")
                .contentType(MediaType.APPLICATION_JSON).content(asJsonString(movie)))
                .andExpect(MockMvcResultMatchers.status().isConflict())
                .andDo(MockMvcResultHandlers.print());
        verify(movieService,times(1)).addMovie(any());
    }
//
//    @Test
//    public void getAllMovies() throws Exception {
//        when(movieService.getAllMovies()).thenReturn(list);
//        mockMvc.perform(MockMvcRequestBuilders.get("/movie-api/v1/movie")
//                .contentType(MediaType.APPLICATION_JSON).content(asJsonString(movie)))
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andDo(MockMvcResultHandlers.print());
//        verify(movieService,times(1)).getAllMovies();
//
//    }
//    @Test
    public void deleteMovie() throws Exception {
        when(movieService.deleteMovie((anyInt()))).thenReturn(resultList);

        mockMvc.perform(MockMvcRequestBuilders.delete("/movie-api/v1/movie/12")
                .contentType(MediaType.APPLICATION_JSON).content(asJsonString(movie)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
        verify(movieService,times(1)).deleteMovie(anyInt());
    }
    @Test
    public void deleteMovieFailure() throws Exception {
        when(movieService.deleteMovie(anyInt())).thenThrow(MovieNotFoundException.class);
        mockMvc.perform(MockMvcRequestBuilders.delete("/movie-api/v1/movie/12")
                .contentType(MediaType.APPLICATION_JSON).content(asJsonString(movie)))
                .andExpect(MockMvcResultMatchers.status().isConflict())
                .andDo(MockMvcResultHandlers.print());
        verify(movieService,times(1)).deleteMovie(anyInt());
    }
//    @Test
//    public void updateMovie() throws Exception {
//        movie.setComments("f");
//        when(movieService.updateMovie(anyInt(),any())).thenReturn(movie);
//        mockMvc.perform(MockMvcRequestBuilders.put("/movie-api/v1/movie/12")
//                .contentType(MediaType.APPLICATION_JSON).content(asJsonString(movie)))
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andDo(MockMvcResultHandlers.print());
//        verify(movieService,times(1)).updateMovie(anyInt(),any());
//    }
    @Test
    public void updateMovieFailure() throws Exception {
        when(movieService.updateMovie(anyInt(),any())).thenThrow(MovieNotFoundException.class);
        mockMvc.perform(MockMvcRequestBuilders.put("/movie-api/v1/movie/12")
                .contentType(MediaType.APPLICATION_JSON).content(asJsonString(movie)))
                .andExpect(MockMvcResultMatchers.status().isConflict())
                .andDo(MockMvcResultHandlers.print());
        verify(movieService,times(1)).updateMovie(anyInt(),any());
    }
    @Test
    public void searchMovie() throws Exception {
        when(movieService.getMovieByTitle(any())).thenReturn(movie);
        mockMvc.perform(MockMvcRequestBuilders.post("/movie-api/v1/movie/la la")
                .contentType(MediaType.APPLICATION_JSON).content(asJsonString(movie)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
        verify(movieService,times(1)).getMovieByTitle(any());
    }
    @Test
    public void searchMovieFailure() throws Exception {
        when(movieService.getMovieByTitle(any())).thenThrow(MovieNotFoundException.class);
        mockMvc.perform(MockMvcRequestBuilders.post("/movie-api/v1/movie/la")
                .contentType(MediaType.APPLICATION_JSON).content(asJsonString(movie)))
                .andExpect(MockMvcResultMatchers.status().isConflict())
                .andDo(MockMvcResultHandlers.print());
        verify(movieService,times(1)).getMovieByTitle(any());
    }

    private static String asJsonString(final Object obj)
    {
        try{
            return new ObjectMapper().writeValueAsString(obj);

        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }

}
