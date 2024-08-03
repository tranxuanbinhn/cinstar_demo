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
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

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
        showTimeModel.setTheater(theaterModel);
        showTimeModel.setScreen(screenModel);
        showTimeModel = showtimeRespository.save(showTimeModel);

        return mapper.map(showTimeModel, ShowTimeDTO.class);
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


    public List<ShowTimeDTO> findAll(LocalDateTime date, Long movieId,Long theaterId ) {
        List<ShowTimeModel> showTimeModels = showtimeRespository.showAll(movieId,theaterId,date);
        if(!this.exists(Constant.ConstantRedis.SHOWTIMESS_PREFIX) || showTimeModels.size()!=this.count(Constant.ConstantRedis.SHOWTIMESS_PREFIX,ShowTimeDTO.class) ) {
            List<ShowTimeDTO> result = new ArrayList<>();
            showTimeModels.stream().forEach(showTimeModel -> {
                ShowTimeDTO showTimeDTO = new ShowTimeDTO();
                showTimeDTO = mapper.map(showTimeModel, ShowTimeDTO.class);
                showTimeDTO.setDate(showTimeModel.getDate());
                showTimeDTO.setMovieId(showTimeModel.getMovie().getId());

                result.add(showTimeDTO);
            });
            List<ShowTimeDTO> listRedis = result;
            listRedis.stream().forEach(showtimeDTO -> {
                showtimeDTO.setDateRedis(Utility.convertDateToString(showtimeDTO.getDate()));
                showtimeDTO.setDate(null);
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
            }).collect(Collectors.toList());
        }
    }

    public List<ShowTimeDTO> findAll( Long movieId,LocalDateTime dateTime ) {
        List<ShowTimeModel> showTimeModels = showtimeRespository.showAll(movieId,dateTime);
        if(!this.exists(Constant.ConstantRedis.SHOWTIMES_PREFIX) || showTimeModels.size()!=this.count(Constant.ConstantRedis.SHOWTIMES_PREFIX,ShowTimeDTO.class) )
        {
            List<ShowTimeDTO> result = new ArrayList<>();
            showTimeModels.stream().forEach(showTimeModel -> {
                ShowTimeDTO showTimeDTO = new ShowTimeDTO();
                showTimeDTO = mapper.map(showTimeModel, ShowTimeDTO.class);
                showTimeDTO.setDate(showTimeModel.getDate());
                showTimeDTO.setMovieId(showTimeModel.getMovie().getId());

                result.add(showTimeDTO);
            });
            List<ShowTimeDTO> listRedis = result;
            listRedis.stream().forEach(showtimeDTO -> {
                showtimeDTO.setDateRedis(Utility.convertDateToString(showtimeDTO.getDate()));
                showtimeDTO.setDate(null);
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
            }).collect(Collectors.toList());

        }

    }
    public List<ShowTimeDTO> findAll(ShowTimeDTO showTimeDTO) {

        return null;
    }

    @Override
    public List<ShowTimeDTO> findAll(Pageable pageable) {
        return null;
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
            result.add(showTimeDTO);
        });
    return result;
    }


}
