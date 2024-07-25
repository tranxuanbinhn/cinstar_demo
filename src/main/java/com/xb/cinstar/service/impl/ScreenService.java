package com.xb.cinstar.service.impl;


import com.xb.cinstar.dto.ScreenDTO;
import com.xb.cinstar.dto.ScreenDTO;
import com.xb.cinstar.exception.ResourceNotFoundException;
import com.xb.cinstar.models.ScreenModel;
import com.xb.cinstar.models.SeatModel;
import com.xb.cinstar.models.TheaterModel;
import com.xb.cinstar.repository.IScreenRespository;
import com.xb.cinstar.repository.ISeatRespository;
import com.xb.cinstar.repository.ITheaterRespository;
import com.xb.cinstar.service.IScreenService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ScreenService implements IScreenService{

    @Autowired private IScreenRespository screenRespository;
    @Autowired private ITheaterRespository theaterRespository;
    @Autowired private ISeatRespository seatRespository;



    @Autowired private ModelMapper mapper;

    public ScreenDTO save(ScreenDTO screenDTO)
    {
        try{
            ScreenModel screenModel = new ScreenModel();
            if(screenDTO.getId()!=null)
            {
                screenModel = screenRespository.findById(screenDTO.getId()).orElseThrow(()->new ResourceNotFoundException("Not found screen update"));
            }
            screenModel = mapper.map(screenDTO, ScreenModel.class);
            List<SeatModel> seatModels = new ArrayList<>();
            List<ScreenModel> list = new ArrayList<>();
            for (Long id:screenDTO.getSeatIds())
            {

                SeatModel seatModel = seatRespository.findById(id).orElseThrow( ()-> new ResourceNotFoundException("Not found seat"));
                list.add(screenModel);
                seatModel.setScreens(list);
                seatModels.add(seatModel);

            }
            screenModel.setSeats(seatModels);

            TheaterModel theaterModel =theaterRespository.findById(screenDTO.getTheaterId()).orElseThrow(()-> new ResourceNotFoundException("Not found theater"));
            screenModel.setTheater(theaterModel);
            screenModel = screenRespository.save(screenModel);
            seatRespository.saveAll(seatModels);
            return mapper.map(screenModel, ScreenDTO.class);


        }
        catch (ResourceNotFoundException e)
        {
            throw new  ResourceNotFoundException("Not found user");
        }
    }

    @Override
    public boolean delete(Long id) {
        try{
            screenRespository.deleteById(id);
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
    public List<ScreenDTO> findAll() {
        return null;
    }

    public List<ScreenDTO> findAll(Pageable pageable)
    {
        Page<ScreenModel> theaters = screenRespository.findAll(pageable);
        List<ScreenDTO> result = new ArrayList<>();
        theaters.getContent().stream().forEach(theater->{
                    ScreenDTO screenDTO = new ScreenDTO();
                    screenDTO = mapper.map(theater, ScreenDTO.class);
                    result.add(screenDTO);
                }
        );
        return result;
    }

    public ScreenDTO findById(Long id){
        if(!screenRespository.existsById(id))
        {
            throw  new ResourceNotFoundException("Not found Theater");
        }
        return mapper.map(screenRespository.findById(id).get(),ScreenDTO.class);
    }


}
