package com.xb.cinstar.service.impl;

import com.xb.cinstar.dto.TicketDTO;
import com.xb.cinstar.exception.ResourceNotFoundException;
import com.xb.cinstar.models.SeatModel;
import com.xb.cinstar.models.ShowTimeModel;
import com.xb.cinstar.models.TicketModel;
import com.xb.cinstar.repository.ISeatRespository;
import com.xb.cinstar.repository.IShowtimeRespository;
import com.xb.cinstar.repository.ITheaterRespository;
import com.xb.cinstar.repository.ITicketRespository;
import com.xb.cinstar.service.ITheaterService;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TicketService {
    @Autowired private ITicketRespository ticketRespository;
    @Autowired private IShowtimeRespository showtimeRespository;
    @Autowired private ISeatRespository seatRespository;

    @Autowired private ModelMapper mapper;
    @Transactional
    public TicketDTO save(TicketDTO ticketDTO)
    {
        try{
            TicketModel ticketModel = new TicketModel();

            ticketModel = mapper.map(ticketDTO, TicketModel.class);
            ShowTimeModel showTimeModel = showtimeRespository.findById(ticketDTO.getShowtimeId())
                    .orElseThrow(()->new ResourceNotFoundException("Not found Showtime"));
            SeatModel seatModel = seatRespository.findById(ticketDTO.getSeatId())
                    .orElseThrow(()->new ResourceNotFoundException("Not found this seat"));
            ticketModel.setShowtime(showTimeModel);
            ticketModel.setSeat(seatModel);

            ticketModel = ticketRespository.save(ticketModel);
            TicketDTO result = mapper.map(ticketModel, TicketDTO.class);
            result.setSeatId(ticketModel.getId());
            result.setShowtimeId(ticketModel.getShowtime().getId());
            seatModel.setStatus(true);
            return result;
        }
        catch (ResourceNotFoundException e)
        {
            throw new  ResourceNotFoundException("Not found user");
        }
    }


    public boolean delete(Long id) {
        try{
            ticketRespository.deleteById(id);
            return  true;
        }
        catch (ResourceNotFoundException e)
        {
            throw new ResourceNotFoundException("Not found this Theater");

        }

    }





    public List<TicketDTO> findAll(Pageable pageable)
    {
        Page<TicketModel> theaters = ticketRespository.findAll(pageable);
        List<TicketDTO> result = new ArrayList<>();
        theaters.getContent().stream().forEach(theater->{
            TicketDTO ticketDTO = new TicketDTO();
            ticketDTO = mapper.map(theater, TicketDTO.class);
            result.add(ticketDTO);
                }
        );
        return result;
    }




}
