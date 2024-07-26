package com.xb.cinstar.service.impl;

import com.xb.cinstar.dto.MovieTheaterDTO;
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
import com.xb.cinstar.service.IScreenService;
import com.xb.cinstar.service.IShowTimeService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ShowTimeService implements IShowTimeService {
    @Autowired private IShowtimeRespository showtimeRespository;
    @Autowired private IMovieRespository movieRespository;
    @Autowired private IScreenRespository screenRespository;
    @Autowired private ITheaterRespository theaterRespository;
    @Autowired private ModelMapper mapper;


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
        List<ShowTimeDTO> result = new ArrayList<>();
        showTimeModels.stream().forEach(showTimeModel -> {
            ShowTimeDTO showTimeDTO = new ShowTimeDTO();
            showTimeDTO = mapper.map(showTimeModel, ShowTimeDTO.class);
            showTimeDTO.setDate(showTimeModel.getDate());
            showTimeDTO.setMovieId(showTimeModel.getMovie().getId());

            result.add(showTimeDTO);
        });
        return result;
    }
    public List<ShowTimeDTO> findAll( Long movieId,LocalDateTime dateTime ) {
        List<ShowTimeModel> showTimeModels = showtimeRespository.showAll(movieId,dateTime);
        List<ShowTimeDTO> result = new ArrayList<>();
        showTimeModels.stream().forEach(showTimeModel -> {
            ShowTimeDTO showTimeDTO = new ShowTimeDTO();
            showTimeDTO = mapper.map(showTimeModel, ShowTimeDTO.class);
            showTimeDTO.setDate(showTimeModel.getDate());
            showTimeDTO.setMovieId(showTimeModel.getMovie().getId());

            result.add(showTimeDTO);
        });
        return result;
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
