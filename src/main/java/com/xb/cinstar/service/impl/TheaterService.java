package com.xb.cinstar.service.impl;

import com.xb.cinstar.dto.TheaterDTO;
import com.xb.cinstar.exception.ResourceNotFoundException;
import com.xb.cinstar.models.TheaterModel;
import com.xb.cinstar.repository.ITheaterRespository;
import com.xb.cinstar.service.ITheaterService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import reactor.core.Exceptions;

import java.util.ArrayList;
import java.util.List;

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
    public List<TheaterDTO> findAll() {
        return null;
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
    return mapper.map(theaterRespository.findById(id).get(),TheaterDTO.class);
    }


}
