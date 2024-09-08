package com.xb.cinstar.service.impl;

import com.xb.cinstar.constant.Constant;
import com.xb.cinstar.dto.MovieDTO;
import com.xb.cinstar.dto.ShowTimeDTO;
import com.xb.cinstar.exception.InvalidRequestException;
import com.xb.cinstar.exception.ResourceNotFoundException;
import com.xb.cinstar.models.MovieModel;
import com.xb.cinstar.models.ScreenModel;
import com.xb.cinstar.models.ShowTimeModel;
import com.xb.cinstar.models.TheaterModel;
import com.xb.cinstar.repository.IMovieRespository;
import com.xb.cinstar.repository.IScreenRespository;
import com.xb.cinstar.repository.IShowtimeRespository;
import com.xb.cinstar.repository.ITheaterRespository;
import com.xb.cinstar.service.IShowTimeService;
import com.xb.cinstar.utility.Utility;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service

public class ShowTimeService extends BaseRedisServiceImpl implements IShowTimeService {
    @Autowired private IShowtimeRespository showtimeRespository;
    @Autowired private IMovieRespository movieRespository;
    @Autowired private IScreenRespository screenRespository;
    @Autowired private ITheaterRespository theaterRespository;
    @Autowired private ModelMapper mapper;

    public ShowTimeService(RedisTemplate<String, Object> redisTemplate) {
        super(redisTemplate);

    }




    @Override
    public ShowTimeDTO save(ShowTimeDTO showTimeDTO) {
        MovieModel movieModel = movieRespository.findById(showTimeDTO.getMovieId()).orElseThrow(()->new ResourceNotFoundException("Not found Movie"));
        ScreenModel screenModel = screenRespository.findById(showTimeDTO.getScreenId()).orElseThrow(()->new ResourceNotFoundException("Not found Screen"));


        TheaterModel theaterModel = theaterRespository.findById(showTimeDTO.getTheaterId()).orElseThrow(()->new ResourceNotFoundException("Not found theater"));
        if(!theaterModel.getScreens().contains(screenModel))
        {
            throw new InvalidRequestException("Invalid screen model");
        }

        ShowTimeModel showTimeModel = new ShowTimeModel();
        showTimeModel.setMovie(movieModel);
        showTimeModel.setDate(showTimeDTO.getDate());
        showTimeModel.setTime(showTimeDTO.getTime());
        showTimeModel.setTheater(theaterModel);
        showTimeModel.setScreen(screenModel);
        showTimeModel.setDateTime();
        showTimeModel = showtimeRespository.save(showTimeModel);

        return mapper.map(showTimeModel, ShowTimeDTO.class);
    }
    public List<ShowTimeDTO> saveMany(List<ShowTimeDTO> showTimeDTOs) {
            List<ShowTimeModel> showTimeModels = new ArrayList<>();
        showTimeDTOs.stream().forEach(showTimeDTO -> {
            MovieModel movieModel = movieRespository.findById(showTimeDTO.getMovieId()).orElseThrow(()->new ResourceNotFoundException("Not found Movie"));
            ScreenModel screenModel = screenRespository.findById(showTimeDTO.getScreenId()).orElseThrow(()->new ResourceNotFoundException("Not found Screen"));
            TheaterModel theaterModel = theaterRespository.findById(showTimeDTO.getTheaterId()).orElseThrow(()->new ResourceNotFoundException("Not found theater"));
            if(!theaterModel.getScreens().contains(screenModel))
            {
                throw new InvalidRequestException("Invalid screen model");

            }
            ShowTimeModel showTimeModel = new ShowTimeModel();
            showTimeModel.setMovie(movieModel);
            showTimeModel.setDate(showTimeDTO.getDate());
            showTimeModel.setTime(showTimeDTO.getTime());
            showTimeModel.setTheater(theaterModel);
            showTimeModel.setScreen(screenModel);
            showTimeModel.setDateTime();
            showTimeModels.add(showTimeModel);
        });





        List<ShowTimeModel> showTimeModel1 = showtimeRespository.saveAll(showTimeModels);
        return showTimeModel1.stream().map(showTimeModel -> mapper.map(showTimeModel, ShowTimeDTO.class)).collect(Collectors.toList());


    }


    @Override
    public boolean delete(Long id) {
        try{
            showtimeRespository.deleteById(id);
            if(this.hashExists(Constant.ConstantRedis.SHOWTIMESS_PREFIX, Constant.ConstantRedis.SHOWTIME_PREFIX+id)
            )
            {
                this.delete(Constant.ConstantRedis.SHOWTIMESS_PREFIX, Constant.ConstantRedis.SHOWTIME_PREFIX+id);
            }
            if(this.hashExists(Constant.ConstantRedis.SHOWTIMES_PREFIX, Constant.ConstantRedis.SHOWTIME_PREFIX+id)
            )
            {
                this.delete(Constant.ConstantRedis.SHOWTIMES_PREFIX, Constant.ConstantRedis.SHOWTIME_PREFIX+id);
            }
            return  true;
        }
        catch (ResourceNotFoundException e)
        {
            throw new ResourceNotFoundException("Not found this Show time");

        }

    }

    @Override
    public boolean update(Long id) {
        return false;
    }

    @Override
    public List<ShowTimeDTO> findAll() {
        return null;
    }

    public void deleteAll (){
        showtimeRespository.deleteAll();
    }
    public List<ShowTimeDTO> findAll(String date, Long movieId,Long theaterId ) {
        Long count = showtimeRespository.countShowAll(movieId, theaterId, date);
        if(!this.exists(Constant.ConstantRedis.SHOWTIMESS_PREFIX) || count!=this.count(Constant.ConstantRedis.SHOWTIMESS_PREFIX,ShowTimeDTO.class) ) {
            List<ShowTimeModel> showTimeModels = showtimeRespository.showAll(movieId,theaterId,date);
            List<ShowTimeDTO> result = new ArrayList<>();
            showTimeModels.stream().forEach(showTimeModel -> {
                ShowTimeDTO showTimeDTO = new ShowTimeDTO();
                showTimeDTO = mapper.map(showTimeModel, ShowTimeDTO.class);
                showTimeDTO.setDate(showTimeModel.getDate());
                showTimeDTO.setMovieId(showTimeModel.getMovie().getId());
                showTimeDTO.setTheaterId(showTimeModel.getTheater().getId());
                showTimeDTO.setTime(showTimeModel.getTime());
                showTimeDTO.setScreenId(showTimeModel.getScreen().getId());
                result.add(showTimeDTO);
            });
            List<ShowTimeDTO> listRedis = result;
            listRedis.stream().forEach(showtimeDTO -> {
                showtimeDTO.setDateRedis(Utility.convertDateToString(showtimeDTO.getDate()));
                showtimeDTO.setDate(null);
                showtimeDTO.setTimeRedis(Utility.convertTimeToString(showtimeDTO.getTime()));
                showtimeDTO.setTime(null);
                this.hashSet(Constant.ConstantRedis.SHOWTIMESS_PREFIX,
                        Constant.ConstantRedis.SHOWTIME_PREFIX+showtimeDTO.getId(),showtimeDTO);
            });
            this.setTimeToLive(Constant.ConstantRedis.SHOWTIMESS_PREFIX,Constant.ConstantRedis.SHOWTIMESS_PREFIX_TTL);
            return result;
        }
        else
        {
            List<ShowTimeDTO> result =this.hashGetAll(Constant.ConstantRedis.SHOWTIMESS_PREFIX, ShowTimeDTO.class);
            return result.stream().peek(showTimeDTO -> {
                showTimeDTO.setDate(Utility.convertStringToDate(showTimeDTO.getDateRedis()));
                showTimeDTO.setTime(Utility.convertStringToTime(showTimeDTO.getTimeRedis()));
            }).collect(Collectors.toList());
        }
    }
    public List<ShowTimeDTO> findAll( String dateTime ) {

       Long count = showtimeRespository.countShowAll(dateTime);

        if(!this.exists(Constant.ConstantRedis.SHOWTIMES_PREFIX) || count!=this.count(Constant.ConstantRedis.SHOWTIMES_PREFIX,ShowTimeDTO.class) )
        {
            List<ShowTimeModel> showTimeModels = showtimeRespository.showAll(dateTime);
            List<ShowTimeDTO> result = new ArrayList<>();
            showTimeModels.stream().forEach(showTimeModel -> {
                ShowTimeDTO showTimeDTO = new ShowTimeDTO();
                showTimeDTO = mapper.map(showTimeModel, ShowTimeDTO.class);
                showTimeDTO.setDate(showTimeModel.getDate());
                showTimeDTO.setMovieId(showTimeModel.getMovie().getId());
                showTimeDTO.setTheaterId(showTimeModel.getTheater().getId());
                showTimeDTO.setTime(showTimeModel.getTime());
                showTimeDTO.setScreenId(showTimeModel.getScreen().getId());
                result.add(showTimeDTO);
            });
            List<ShowTimeDTO> listRedis = result;
            listRedis.stream().forEach(showtimeDTO -> {
                showtimeDTO.setDateRedis(Utility.convertDateToString(showtimeDTO.getDate()));
                showtimeDTO.setDate(null);

                showtimeDTO.setTimeRedis(Utility.convertTimeToString(showtimeDTO.getTime()));
                showtimeDTO.setTime(null);
                this.hashSet(Constant.ConstantRedis.SHOWTIMES_PREFIX,
                        Constant.ConstantRedis.SHOWTIME_PREFIX+showtimeDTO.getId(),showtimeDTO);
            });
            this.setTimeToLive(Constant.ConstantRedis.SHOWTIMES_PREFIX,Constant.ConstantRedis.SHOWTIMES_PREFIX_TTL);

            return result;

        }
        else
        {
            List<ShowTimeDTO> result = this.hashGetAll(Constant.ConstantRedis.SHOWTIMES_PREFIX, ShowTimeDTO.class);
            return result.stream().peek(showTimeDTO -> {
                showTimeDTO.setDate(Utility.convertStringToDate(showTimeDTO.getDateRedis()));
                showTimeDTO.setTime(Utility.convertStringToTime(showTimeDTO.getTimeRedis()));
            }).collect(Collectors.toList());

        }

    }

    public List<ShowTimeDTO> findAll( Long movieId,String dateTime ) {
        dateTime = dateTime.substring(0, 10);
       Long count = showtimeRespository.countShowAll(movieId, dateTime);
        if(!this.exists(Constant.ConstantRedis.SHOWTIMES_PREFIX) ||count!=this.count(Constant.ConstantRedis.SHOWTIMES_PREFIX,ShowTimeDTO.class) )
        {
            List<ShowTimeModel> showTimeModels = showtimeRespository.showAll(movieId,dateTime);
            List<ShowTimeDTO> result = new ArrayList<>();
            showTimeModels.stream().forEach(showTimeModel -> {
                ShowTimeDTO showTimeDTO = new ShowTimeDTO();
                showTimeDTO = mapper.map(showTimeModel, ShowTimeDTO.class);
                showTimeDTO.setDate(showTimeModel.getDate());
                showTimeDTO.setMovieId(showTimeModel.getMovie().getId());
                showTimeDTO.setTheaterId(showTimeModel.getTheater().getId());
                showTimeDTO.setTime(showTimeModel.getTime());
                showTimeDTO.setScreenId(showTimeModel.getScreen().getId());
                result.add(showTimeDTO);
            });
            List<ShowTimeDTO> listRedis = result;
            listRedis.stream().forEach(showtimeDTO -> {
                showtimeDTO.setDateRedis(Utility.convertDateToString(showtimeDTO.getDate()));
                showtimeDTO.setDate(null);
                showtimeDTO.setTimeRedis(Utility.convertTimeToString(showtimeDTO.getTime()));
                showtimeDTO.setTime(null);
                this.hashSet(Constant.ConstantRedis.SHOWTIMES_PREFIX,
                        Constant.ConstantRedis.SHOWTIME_PREFIX+showtimeDTO.getId(),showtimeDTO);
            });
            this.setTimeToLive(Constant.ConstantRedis.SHOWTIMES_PREFIX,Constant.ConstantRedis.SHOWTIMES_PREFIX_TTL);

            return result;

        }
        else
        {
           List<ShowTimeDTO> result = this.hashGetAll(Constant.ConstantRedis.SHOWTIMES_PREFIX, ShowTimeDTO.class);
            return result.stream().peek(showTimeDTO -> {
                showTimeDTO.setDate(Utility.convertStringToDate(showTimeDTO.getDateRedis()));
                showTimeDTO.setTime(Utility.convertStringToTime(showTimeDTO.getTimeRedis()));
            }).collect(Collectors.toList());

        }

    }
    public List<ShowTimeDTO> findAll(ShowTimeDTO showTimeDTO) {

        return null;
    }
    public Long count (){
        return showtimeRespository.count();
    }
    @Override
    public List<ShowTimeDTO> findAll(Pageable pageable) {
        Page<ShowTimeModel> showTimeModels = showtimeRespository.findAll(pageable);
       return showTimeModels.stream().map(showTimeModel -> {
            ShowTimeDTO showTimeDTO = mapper.map(showTimeModel, ShowTimeDTO.class);
            showTimeDTO.setMovieId(showTimeModel.getMovie().getId());
            showTimeDTO.setScreenName(showTimeModel.getScreen().getName());
            showTimeDTO.setMovieTitle(showTimeModel.getMovie().getTitle());
            showTimeDTO.setTheaterName(showTimeModel.getTheater().getName());
            if(showTimeModel.getTheater().getId()!=null){
                showTimeDTO.setTheaterId(showTimeModel.getTheater().getId());
            }
            else{
                showTimeDTO.setTheaterId(null);
            }
            showTimeDTO.setScreenId(showTimeModel.getScreen().getId());
            return showTimeDTO;
        }).collect(Collectors.toList());

    }

    public List<ShowTimeDTO> findByMovieId(Long movieId) throws ResourceNotFoundException
    {
        List<ShowTimeModel> showTimeModels = showtimeRespository.findAllByMovieId(movieId);
        List<ShowTimeDTO> result = new ArrayList<>();
        showTimeModels.stream().forEach(showTimeModel ->{
            ShowTimeDTO showTimeDTO = new ShowTimeDTO();
            showTimeDTO = mapper.map(showTimeModel, ShowTimeDTO.class);
            showTimeDTO.setDate(showTimeModel.getDate());
            showTimeDTO.setMovieId(showTimeModel.getMovie().getId());
            showTimeDTO.setScreenId(showTimeModel.getScreen().getId());
            result.add(showTimeDTO);
        });
    return result;
    }
    public List<ShowTimeDTO> findThreeByMovieId(Long movieId) throws ResourceNotFoundException
    {
        List<ShowTimeModel> showTimeModels = showtimeRespository.findThreeByMovieId(movieId);
        List<ShowTimeDTO> result = new ArrayList<>();
        showTimeModels.stream().forEach(showTimeModel ->{
            ShowTimeDTO showTimeDTO = new ShowTimeDTO();
            showTimeDTO = mapper.map(showTimeModel, ShowTimeDTO.class);
            showTimeDTO.setDate(showTimeModel.getDate());
            showTimeDTO.setTime(showTimeModel.getTime());
            showTimeDTO.setMovieId(showTimeModel.getMovie().getId());
            result.add(showTimeDTO);
        });
        return result;
    }
    public List<ShowTimeDTO> findShowTimeFromCurrentDateToTwoDateLater() throws ResourceNotFoundException
    {
        List<ShowTimeModel> showTimeModels = showtimeRespository.selectShowTimeFromCurrentDateToTwoDateLater();
        List<ShowTimeDTO> result = new ArrayList<>();
        showTimeModels.stream().forEach(showTimeModel ->{
            ShowTimeDTO showTimeDTO = new ShowTimeDTO();
            showTimeDTO = mapper.map(showTimeModel, ShowTimeDTO.class);
            showTimeDTO.setDate(showTimeModel.getDate());
            showTimeDTO.setTime(showTimeModel.getTime());
            showTimeDTO.setMovieId(showTimeModel.getMovie().getId());
            result.add(showTimeDTO);
        });
        return result;
    }
    public  List<ShowTimeDTO> findShowtimeFromCurrentDate(){
        List<ShowTimeModel> showTimeModels = showtimeRespository.findShowtimeGreateThanCurrentDate();
        return showTimeModels.stream().map(showTimeModel -> mapper.map(showTimeModel, ShowTimeDTO.class))
                .collect(Collectors.toList());
    }


}
