package com.xb.cinstar.controllers.admin;

import com.xb.cinstar.dto.MovieDTO;
import com.xb.cinstar.models.ETypeMovie;
import com.xb.cinstar.payload.response.PageResponse;
import com.xb.cinstar.pojo.MoviePOJO;
import com.xb.cinstar.pojo.RequestPOJO;
import com.xb.cinstar.pojo.ResultPOJO;
import com.xb.cinstar.service.impl.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/test/movie")
public class MovieContronllerAdmin {
    @Autowired
    private MovieService movieService;
    @GetMapping("/upcoming")
    public ResponseEntity<?> getUpcomingMovie()
    {
        ResultPOJO resultPOJO = movieService.getUpcomingMovie();
        return new ResponseEntity<>(resultPOJO, HttpStatus.OK);
    }

    @GetMapping("/nowshowing")
    public ResponseEntity<?> getShowingMovie()
    {
        ResultPOJO resultPOJO = movieService.getNowShowingMovie();
        return new ResponseEntity<>(resultPOJO, HttpStatus.OK);
    }
    @GetMapping("/detail")
    public ResponseEntity<?> getDetailMovie(@RequestParam("id")Long id)
    {
        MoviePOJO moviePOJO = movieService.getDetailMovie(id);
        return new ResponseEntity<>(moviePOJO, HttpStatus.OK);
    }
    @PostMapping("/add")
    public ResponseEntity<?> save(@RequestBody RequestPOJO requestPOJO)
    {
        List<Long> arr = new ArrayList<>();
        arr.add(4L);
        MovieDTO movieDTO = movieService.saveMovie(requestPOJO.getMoviePOJO(), requestPOJO.getTypeMovie(),requestPOJO.getTheaterIds());
        return new ResponseEntity<>(movieDTO, HttpStatus.OK);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteMovie(@PathVariable Long id)
    {
        if(movieService.deleteMovieById(id))
        {
            return new ResponseEntity<>( HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping()
    public ResponseEntity<?> getAllMovie(@RequestParam("page")int page,@RequestParam("limit")int limit)
    {
        Pageable pageable = PageRequest.of(page-1, limit);
        List<MovieDTO> result = movieService.getAllMovie(pageable);
        PageResponse pageResponse = new PageResponse();
        pageResponse.setResults(result);
        pageResponse.setPage(page);
        pageResponse.setLimit(limit);
        return new ResponseEntity<>(pageResponse, HttpStatus.OK);
    }
}
