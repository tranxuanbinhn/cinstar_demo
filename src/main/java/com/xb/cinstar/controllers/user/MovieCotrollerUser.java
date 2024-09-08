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
@CrossOrigin("${allowed.origin}")
@RestController
@RequestMapping("/api/user/movie")
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
    @GetMapping("/showing/{id}")
    public ResponseEntity<?> getShowingMovieByTheater(@PathVariable Long id)
    {

        List<MovieDTO> result = movieService.getShowingMovieByTheaterId(id);


        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    @GetMapping("/upcomming/{id}")
    public ResponseEntity<?> getUpcommingMovieByTheater(@PathVariable Long id)
    {

        List<MovieDTO> result = movieService.getUpcommingMovieByTheaterId(id);


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

    @GetMapping("/newmovie")
    public ResponseEntity<?> getNewMovie()
    {

        List<MovieDTO> result = movieService.showFiveFilmNew();


        return new ResponseEntity<>(result, HttpStatus.OK);
    };







}
