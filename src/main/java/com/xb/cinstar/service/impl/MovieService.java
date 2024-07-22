package com.xb.cinstar.service.impl;

import com.xb.cinstar.dto.MovieDTO;
import com.xb.cinstar.exception.ResourceNotFoundException;
import com.xb.cinstar.models.ETypeMovie;
import com.xb.cinstar.models.MovieModel;
import com.xb.cinstar.pojo.MoviePOJO;
import com.xb.cinstar.pojo.ResultPOJO;
import com.xb.cinstar.repository.IMovieRespository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;


@Service
public class MovieService {
    @Value("${themoviedb.accessToken}")
    private String accessToken;
    @Value("${themoviedb.url.main}")
    private  String urlMain;
    @Autowired
    private RestTemplate restTemplate;
    @Value("${themoviedb.url}")
    private String apiUrl;
    @Value("${themoviedb.url.showing}")
    private String apiUrlSh;

    @Autowired private IMovieRespository movieRespository;

    @Autowired
    private ModelMapper mapper ;
    public ResultPOJO getUpcomingMovie() {

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);
        headers.set("accept", "application/json");
        HttpEntity<String> request = new HttpEntity<>(headers);

        ResponseEntity<ResultPOJO> response = restTemplate
                .exchange(apiUrl, HttpMethod.GET, request, ResultPOJO.class);
        ResultPOJO movie = response.getBody();
        return movie;

    }

    public ResultPOJO getNowShowingMovie() {

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);
        headers.set("accept", "application/json");
        HttpEntity<String> request = new HttpEntity<>(headers);

        ResponseEntity<ResultPOJO> response = restTemplate
                .exchange(apiUrlSh, HttpMethod.GET, request, ResultPOJO.class);
        ResultPOJO movie = response.getBody();
        return movie;

    }

    public MovieDTO getDetailAndSaveMovie(Long id, ETypeMovie eTypeMovie) {

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);
        headers.set("accept", "application/json");
        HttpEntity<String> request = new HttpEntity<>(headers);

        ResponseEntity<MoviePOJO> response = restTemplate
                .exchange(urlMain+"/"+id+"?language=en-US", HttpMethod.GET, request, MoviePOJO.class);
        MoviePOJO movie = response.getBody();

        MovieDTO movieDTO = new MovieDTO();
        movieDTO = mapper.map(movie, MovieDTO.class);
        movieDTO.setIdCode(movie.getId());
        movieDTO.setTypeMovie(eTypeMovie);
        movieDTO = saveMovie(movieDTO);
        return  movieDTO;
    }

    public MovieDTO saveMovie(MovieDTO movieDTO) throws ResourceNotFoundException
    {
        MovieModel movieModel = mapper.map(movieDTO, MovieModel.class);
        movieModel = movieRespository.save(movieModel);
        return  mapper.map(movieModel, MovieDTO.class);
    }

    public boolean deleteMovieById(Long id)
    {
        try{
            movieRespository.deleteById(id);
            return  true;
        }
        catch (ResourceNotFoundException e)
        {

            throw  new ResourceNotFoundException("Not found Movie");
        }
    }

    public List<MovieDTO> getAllMovie(Pageable pageable) {

        List<MovieDTO> result = new ArrayList<>();
        Page<MovieModel> movieModels = movieRespository.findAll(pageable);
        movieModels.getContent().forEach(movieModel -> {
            MovieDTO movieDTO = new MovieDTO();
            movieDTO = mapper.map(movieModel, MovieDTO.class);
            result.add(movieDTO);
        });
        return result;
    }
    public List<MovieDTO> getShowingMovie(Pageable pageable) {

        List<MovieDTO> result = new ArrayList<>();
        Page<MovieModel> movieModels = movieRespository.findAll(pageable);
        movieModels.getContent().forEach(movieModel -> {
            MovieDTO movieDTO = new MovieDTO();
            movieDTO = mapper.map(movieModel, MovieDTO.class);
            if(movieModel.getTypeMovie().equals("MOVIE_NOWSHOWING"))
            {
                result.add(movieDTO);
            }

        });
        return result;
    }
    public List<MovieDTO> getCommingSoonMovie(Pageable pageable) {

        List<MovieDTO> result = new ArrayList<>();
        Page<MovieModel> movieModels = movieRespository.findAll(pageable);
        movieModels.getContent().forEach(movieModel -> {
            MovieDTO movieDTO = new MovieDTO();
            movieDTO = mapper.map(movieModel, MovieDTO.class);
            if(movieModel.getTypeMovie().equals("MOVIE_COMINGSOON"))
            {
                result.add(movieDTO);
            }

        });
        return result;
    }





}
