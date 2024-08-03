package com.xb.cinstar.service.impl;

import com.xb.cinstar.dto.ShowTimeDTO;
import com.xb.cinstar.dto.TheaterDTO;
import com.xb.cinstar.exception.ResourceNotFoundException;
import com.xb.cinstar.models.MovieModel;
import com.xb.cinstar.models.TheaterModel;
import com.xb.cinstar.repository.ITheaterRespository;
import com.xb.cinstar.service.ITheaterService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import reactor.core.Exceptions;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service

public class TheaterService implements ITheaterService {
    @Autowired private ITheaterRespository theaterRespository;

    @Autowired private ModelMapper mapper;

    public TheaterDTO save(TheaterDTO theaterDTO)
    {
        try{
            TheaterModel theaterModel = new TheaterModel();
            if(theaterDTO.getId()!=null)
            {
                theaterModel =theaterRespository.findById(theaterDTO.getId()).get();

            }
            theaterModel = mapper.map(theaterDTO, TheaterModel.class);
            theaterModel = theaterRespository.save(theaterModel);
            return mapper.map(theaterModel, TheaterDTO.class);
        }
        catch (ResourceNotFoundException e)
        {
            throw new  ResourceNotFoundException("Not found user");
        }
    }

    @Override
    public boolean delete(Long id) {
        try{
            theaterRespository.deleteById(id);
            return  true;
        }
        catch (ResourceNotFoundException e)
        {
            throw new ResourceNotFoundException("Not found this Theater");

        }

    }



    @Override
    public boolean update(Long id) {
        return false;
    }


    @Override
    public List<TheaterDTO> findAll() throws ResourceNotFoundException{
        List<TheaterModel> theaters = theaterRespository.findAll();
        List<TheaterDTO> result = new ArrayList<>();
        theaters.stream().forEach(theater->{
                    TheaterDTO theaterDTO = new TheaterDTO();
                    theaterDTO = mapper.map(theater, TheaterDTO.class);
                    result.add(theaterDTO);
                }
        );
        return result;

    }

    public List<TheaterDTO> findAll(Pageable pageable)
    {
        Page<TheaterModel> theaters = theaterRespository.findAll(pageable);
        List<TheaterDTO> result = new ArrayList<>();
        theaters.getContent().stream().forEach(theater->{
                    TheaterDTO theaterDTO = new TheaterDTO();
                    theaterDTO = mapper.map(theater, TheaterDTO.class);
                    result.add(theaterDTO);
                }
        );
        return result;
    }

    public TheaterDTO findById(Long id){
    if(!theaterRespository.existsById(id))
    {
        throw  new ResourceNotFoundException("Not found Theater");
    }
    Optional<TheaterModel> theaterModel = theaterRespository.findById(id);
    List<Long> ids = new ArrayList<>();
        theaterModel.get().getMovies().stream().forEach(MovieModel->{
            ids.add(MovieModel.getId());
        });
    TheaterDTO result = mapper.map(theaterModel.get(),TheaterDTO.class);
    result.setMovieIds(ids);
    return result;
    }

    public List<TheaterDTO> findTheaterByCity(String city)
    {
        List<TheaterModel> theaterModels = theaterRespository.findAllByCity(city);
        List<TheaterDTO> result = new ArrayList<>();
        theaterModels.stream().forEach(theaterModel -> {
            TheaterDTO theaterDTO = new TheaterDTO();
            theaterDTO = mapper.map(theaterModel, TheaterDTO.class);
            result.add(theaterDTO);
        });
        return  result;
    }


}
