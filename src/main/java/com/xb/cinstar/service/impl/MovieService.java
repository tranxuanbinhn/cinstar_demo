package com.xb.cinstar.service.impl;

import com.xb.cinstar.constant.Constant;
import com.xb.cinstar.dto.MovieDTO;
import com.xb.cinstar.dto.MovieTheaterDTO;
import com.xb.cinstar.dto.TheaterDTO;
import com.xb.cinstar.dto.UserDTO;
import com.xb.cinstar.exception.ResourceNotFoundException;
import com.xb.cinstar.models.ETypeMovie;
import com.xb.cinstar.models.MovieModel;
import com.xb.cinstar.models.TheaterModel;
import com.xb.cinstar.pojo.MoviePOJO;
import com.xb.cinstar.pojo.ResultPOJO;
import com.xb.cinstar.repository.IMovieRespository;
import com.xb.cinstar.repository.ITheaterRespository;
import com.xb.cinstar.service.IGenricService;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;


@Service

public class MovieService  extends BaseRedisServiceImpl implements IGenricService<MovieDTO>{
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


    public MovieService(RedisTemplate<String, Object> redisTemplate) {
        super(redisTemplate);
    }

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
       MovieDTO resuslt = mapper.map(movieModel, MovieDTO.class);

       if(resuslt.getTypeMovie().equals("MOVIE_NOWSHOWING"))
       {
            this.hashSet(Constant.ConstantRedis.MOVIE_NOWSHOWING_PREFIX,Constant.ConstantRedis.MOVIE_PREFIX+resuslt.getId(),
                    resuslt);
            this.setTimeToLive(Constant.ConstantRedis.MOVIE_NOWSHOWING_PREFIX, Constant.ConstantRedis.MOVIE_NOWSHOWING_PREFIX_TTL);
       }
        if(resuslt.getTypeMovie().equals("MOVIE_COMINGSOON"))
        {
            this.hashSet(Constant.ConstantRedis.MOVIES_UPCOMMING_PREFIX,Constant.ConstantRedis.MOVIE_PREFIX+resuslt.getId(),
                    resuslt);
            this.setTimeToLive(Constant.ConstantRedis.MOVIES_UPCOMMING_PREFIX,Constant.ConstantRedis.MOVIES_UPCOMMING_PREFIX_TTL);
        }

    return resuslt ;

    }

    public boolean deleteMovieById(Long id)
    {
        try{
            movieRespository.deleteById(id);
            if(this.exists(Constant.ConstantRedis.MOVIE_PREFIX+id))
            {
                this.delete(Constant.ConstantRedis.MOVIE_PREFIX+id);
            }
            if(this.hashExists(Constant.ConstantRedis.MOVIE_NOWSHOWING_PREFIX,Constant.ConstantRedis.MOVIE_PREFIX+id)
            )
            {
                this.delete(Constant.ConstantRedis.MOVIE_NOWSHOWING_PREFIX,Constant.ConstantRedis.MOVIE_PREFIX+id);
            }
            if(this.hashExists(Constant.ConstantRedis.MOVIES_UPCOMMING_PREFIX,Constant.ConstantRedis.MOVIE_PREFIX+id)
            )
            {
                this.delete(Constant.ConstantRedis.MOVIES_UPCOMMING_PREFIX,Constant.ConstantRedis.MOVIE_PREFIX+id);
            }


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
        if(this.exists(Constant.ConstantRedis.MOVIE_NOWSHOWING_PREFIX))
        {

            return this.hashGetAll(Constant.ConstantRedis.MOVIE_NOWSHOWING_PREFIX, MovieDTO.class);
        }
        else
        {
            List<MovieModel> movieModels = movieRespository.findAll();
            movieModels.forEach(movieModel -> {
                MovieDTO movieDTO =  mapper.map(movieModel, MovieDTO.class);
                if(movieModel.getTypeMovie().name().equals("MOVIE_NOWSHOWING"))
                {
                    result.add(movieDTO);
                }

            });
            result.stream().forEach(movieDTO -> {
                this.hashSet(Constant.ConstantRedis.MOVIE_NOWSHOWING_PREFIX,
                        Constant.ConstantRedis.MOVIE_PREFIX+movieDTO.getId(),movieDTO);
            });
            this.setTimeToLive(Constant.ConstantRedis.MOVIE_NOWSHOWING_PREFIX,Constant.ConstantRedis.MOVIE_NOWSHOWING_PREFIX_TTL);
            return result;
        }

    }

    public List<MovieDTO> getCommingSoonMovie() {

        List<MovieDTO> result = new ArrayList<>();
        if(this.exists(Constant.ConstantRedis.MOVIES_UPCOMMING_PREFIX))
        {
//            List<Object> objects = this.get(Constant.ConstantRedis.MOVIES_UPCOMMING_PREFIX);
           return this.hashGetAll(Constant.ConstantRedis.MOVIES_UPCOMMING_PREFIX, MovieDTO.class);
        }
        else
        {
            List<MovieModel> movieModels = movieRespository.findAll();
            movieModels.forEach(movieModel -> {
                MovieDTO movieDTO = mapper.map(movieModel, MovieDTO.class);
                if(movieModel.getTypeMovie().name().equals("MOVIE_COMINGSOON"))
                {
                    result.add(movieDTO);
                }

            });
            result.stream().forEach(movieDTO -> {
                this.hashSet(Constant.ConstantRedis.MOVIES_UPCOMMING_PREFIX,
                    Constant.ConstantRedis.MOVIE_PREFIX+movieDTO.getId(),movieDTO);
            });
            this.setTimeToLive(Constant.ConstantRedis.MOVIES_UPCOMMING_PREFIX,Constant.ConstantRedis.MOVIES_UPCOMMING_PREFIX_TTL);
            return result;
        }

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
        String prefix = Constant.ConstantRedis.MOVIE_PREFIX;
        if(this.exists(prefix+id))
        {
            return (MovieDTO) this.get(prefix+id);
        }
        else {
            MovieModel movieModel = movieRespository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Not found this movie"));
            MovieDTO result = mapper.map(movieModel, MovieDTO.class);
            List<Long> theaterids = new ArrayList<>();
            movieModel.getTheaters().forEach(theaterModel -> {
                theaterids.add(theaterModel.getId());
                result.setTheaterIds(theaterids);
            });

            MovieDTO movieDTO = mapper.map(movieModel, MovieDTO.class);
            this.set(prefix+result.getId(),movieDTO);
            this.setTimeToLive(prefix+result.getId(),Constant.ConstantRedis.MOVIE_PREFIX_TTL);
            return  movieDTO;
        }
    }

    @Override
    public MovieDTO save(MovieDTO movieDTO) {
        return null;
    }

    @Override
    public boolean delete(Long id) {
        return false;
    }

    @Override
    public boolean update(Long id) {
        return false;
    }

    @Override
    public List<MovieDTO> findAll() {
        return null;
    }

    @Override
    public List<MovieDTO> findAll(Pageable pageable) {
        return null;
    }
}
