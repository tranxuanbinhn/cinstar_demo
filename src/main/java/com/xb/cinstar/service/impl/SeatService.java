package com.xb.cinstar.service.impl;

import com.xb.cinstar.dto.SeatDTO;
import com.xb.cinstar.models.SeatModel;
import com.xb.cinstar.repository.ISeatRespository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SeatService {
    @Autowired private ISeatRespository seatRespository;
    @Autowired private ModelMapper mapper;

    public SeatDTO save(SeatDTO seatDTO)
    {
        SeatModel seatModel = new SeatModel();
        seatModel = mapper.map(seatDTO,SeatModel.class);

        seatModel = seatRespository.save(seatModel);
        return  mapper.map(seatModel, SeatDTO.class);

    }

}
