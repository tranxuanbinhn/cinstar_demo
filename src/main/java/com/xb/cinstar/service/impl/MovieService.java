package com.xb.cinstar.service.impl;

import com.xb.cinstar.dto.MovieDTO;
import com.xb.cinstar.dto.MovieTheaterDTO;
import com.xb.cinstar.dto.TheaterDTO;
import com.xb.cinstar.exception.ResourceNotFoundException;
import com.xb.cinstar.models.ETypeMovie;
import com.xb.cinstar.models.MovieModel;
import com.xb.cinstar.models.TheaterModel;
import com.xb.cinstar.pojo.MoviePOJO;
import com.xb.cinstar.pojo.ResultPOJO;
import com.xb.cinstar.repository.IMovieRespository;
import com.xb.cinstar.repository.ITheaterRespository;
import jakarta.transaction.Transactional;
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
    @Autowired private ITheaterRespository theaterRespository;

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

    public MoviePOJO getDetailMovie(Long id ) {

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);
        headers.set("accept", "application/json");
        HttpEntity<String> request = new HttpEntity<>(headers);

        ResponseEntity<MoviePOJO> response = restTemplate
                .exchange(urlMain+"/"+id+"?language=en-US", HttpMethod.GET, request, MoviePOJO.class);
        MoviePOJO movie = response.getBody();


        return  movie;
    }
    @Transactional
    public MovieDTO saveMovie(MoviePOJO moviePOJO, ETypeMovie typeMovie, List<Long> theaterIds) throws ResourceNotFoundException
    {
        MovieDTO movieDTO = new MovieDTO();
        movieDTO = mapper.map(moviePOJO, MovieDTO.class);
        movieDTO.setIdCode(movieDTO.getId());
        movieDTO.setTypeMovie(typeMovie);

        MovieModel movieModel = mapper.map(movieDTO, MovieModel.class);
        List<TheaterModel> theaters = new ArrayList<>();
        theaterIds.stream().forEach(id ->
        {
            TheaterModel theaterModel = theaterRespository.findById(id).orElseThrow(()->new ResourceNotFoundException("Not found theater"));
            theaters.add(theaterModel);
        });
        movieModel.setTheaters(theaters);
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
    public List<MovieDTO> getShowingMovie() {

        List<MovieDTO> result = new ArrayList<>();
        List<MovieModel> movieModels = movieRespository.findAll();
        movieModels.forEach(movieModel -> {
            MovieDTO movieDTO = new MovieDTO();
            movieDTO = mapper.map(movieModel, MovieDTO.class);
            if(movieModel.getTypeMovie().name().equals("MOVIE_NOWSHOWING"))
            {
                result.add(movieDTO);
            }

        });
        return result;
    }
    public List<MovieDTO> getCommingSoonMovie() {

        List<MovieDTO> result = new ArrayList<>();
        List<MovieModel> movieModels = movieRespository.findAll();
        movieModels.forEach(movieModel -> {
            MovieDTO movieDTO = new MovieDTO();
            movieDTO = mapper.map(movieModel, MovieDTO.class);
            if(movieModel.getTypeMovie().name().equals("MOVIE_COMINGSOON"))
            {
                result.add(movieDTO);
            }

        });
        return result;
    }

    public MovieTheaterDTO findMovieOrTheater(String keyword) {

        List<MovieModel> movieModels = movieRespository.findMovieByKeyword(keyword);
        List<TheaterModel> theaterModels = theaterRespository.findTheaterByKeyword(keyword);
        List<MovieDTO> resultMovie = new ArrayList<>();
        movieModels.stream().forEach(movieModel -> {
            MovieDTO movieDTO = new MovieDTO();
            movieDTO=mapper.map(movieModel, MovieDTO.class);
            resultMovie.add(movieDTO);
        });
        List<TheaterDTO> resulTheater = new ArrayList<>();
        theaterModels.stream().forEach(theaterModel -> {
            TheaterDTO theaterDTO = new TheaterDTO();
            theaterDTO=mapper.map(theaterModel, TheaterDTO.class);
            resulTheater.add(theaterDTO);
        });


        MovieTheaterDTO movieTheaterDTO = new MovieTheaterDTO();
        movieTheaterDTO.setMovieDTOs(resultMovie);
        movieTheaterDTO.setTheaterDTOs(resulTheater);

        return movieTheaterDTO;
    }



    public MovieDTO findMovieInDataBase(Long id)
    {
        MovieModel movieModel = movieRespository.findById(id).orElseThrow(()->new ResourceNotFoundException("Not found this movie"));
        MovieDTO result = mapper.map(movieModel, MovieDTO.class);
        List<Long> theaterids = new ArrayList<>();
        movieModel.getTheaters().forEach(theaterModel -> {
        theaterids.add(theaterModel.getId());
        result.setTheaterIds(theaterids);
        });
       return mapper.map(movieModel, MovieDTO.class);
    }

}
