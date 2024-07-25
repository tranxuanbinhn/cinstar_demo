package com.xb.cinstar.controllers.user;

import com.xb.cinstar.dto.MovieDTO;
import com.xb.cinstar.dto.MovieTheaterDTO;
import com.xb.cinstar.payload.response.PageResponse;
import com.xb.cinstar.service.impl.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/test/user/movie")
public class MovieCotrollerUser {
    @Autowired private MovieService movieService;
    @GetMapping("/upcomming")
    public ResponseEntity<?> getUpcomingMovie()
    {

        List<MovieDTO> result = movieService.getCommingSoonMovie();


        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    @GetMapping("/showing")
    public ResponseEntity<?> getShowingMovie()
    {

        List<MovieDTO> result = movieService.getShowingMovie();


        return new ResponseEntity<>(result, HttpStatus.OK);
    }

        @GetMapping("/findmovie")
        public ResponseEntity<?> findMovieAndTheater(@RequestParam("keyword")String keword)
        {

            MovieTheaterDTO result = movieService.findMovieOrTheater(keword);


            return new ResponseEntity<>(result, HttpStatus.OK);
        };


    @GetMapping("/getdetail/{id}")
    public ResponseEntity<?> getDetailMovie(@PathVariable Long id)
    {

        MovieDTO result = movieService.findMovieInDataBase(id);


        return new ResponseEntity<>(result, HttpStatus.OK);
    };






}
