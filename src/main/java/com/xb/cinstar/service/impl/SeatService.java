package com.xb.cinstar.service.impl;

import com.xb.cinstar.dto.SeatDTO;
import com.xb.cinstar.models.ETypeSeat;
import com.xb.cinstar.models.SeatModel;

import com.xb.cinstar.repository.ISeatRespository;
import com.xb.cinstar.repository.ITicketRelationRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SeatService {
    @Autowired private ISeatRespository seatRespository;
    @Autowired private ModelMapper mapper;
    @Autowired private ITicketRelationRepository iTicketRelationRepository ;
    public SeatDTO save(SeatDTO seatDTO)
    {

        SeatModel seatModel = new SeatModel();
        seatModel = mapper.map(seatDTO,SeatModel.class);

        seatModel = seatRespository.save(seatModel);
        return  mapper.map(seatModel, SeatDTO.class);

    }
    public void save()
    {
        List<String> rows = Arrays.asList("A", "B", "C", "D", "E", "F", "G", "H", "I", "J");

        for (String row : rows) {
            for (int i = 1; i <= 10; i++) {
                SeatDTO seatDTO = new SeatDTO();
                seatDTO.setName(row + i);
                seatDTO.setRowSeat(row);
                seatDTO.setNumber(i);
                seatDTO.setStatus(false);
                seatDTO.setTypeSeat(ETypeSeat.SEAT_ALONE);

                SeatModel seatModel = mapper.map(seatDTO, SeatModel.class);
                seatRespository.save(seatModel);
            }
        }
    }



    public List<SeatDTO> findByScreenId(Long screenId)
    {

        List<SeatModel> seatModels = seatRespository.findByScreenId(screenId);
        return  seatModels.stream().map(SeatModel -> {
            SeatDTO seatDTO = mapper.map(SeatModel, SeatDTO.class);
            seatDTO.setStatus(SeatModel.isStatus());
            return  seatDTO;
        }).collect(Collectors.toList());

    }
    public List<SeatDTO> findByScreenIdAndShowTimeId(Long screenId, Long showtimeid)
    {

        List<SeatModel> seatModels = seatRespository.findByScreenId(screenId);
        return  seatModels.stream().map(SeatModel -> {
            SeatDTO seatDTO = mapper.map(SeatModel, SeatDTO.class);
            if(iTicketRelationRepository.countTicketRelationByShowtimeAndSeat(showtimeid, seatDTO.getId())>0)
            {
                seatDTO.setStatus(true);
            }
            else{
                seatDTO.setStatus(false);
            }

            return  seatDTO;
        }).collect(Collectors.toList());

    }
    public  void delete(Long[] ids)
    {
        for (Long id : ids) {
            seatRespository.deleteById(id);
            
        }
    }

}
