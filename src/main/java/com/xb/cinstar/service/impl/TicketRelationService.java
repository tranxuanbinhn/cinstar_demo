package com.xb.cinstar.service.impl;

import com.xb.cinstar.dto.TicketOrderDTO;
import com.xb.cinstar.dto.TicketRelationDTO;
import com.xb.cinstar.exception.ResourceNotFoundException;
import com.xb.cinstar.models.SeatModel;
import com.xb.cinstar.models.ShowTimeModel;
import com.xb.cinstar.models.TicketModel;
import com.xb.cinstar.models.TicketRelation;
import com.xb.cinstar.repository.ISeatRespository;
import com.xb.cinstar.repository.IShowtimeRespository;
import com.xb.cinstar.repository.ITicketRelationRepository;
import com.xb.cinstar.repository.ITicketRespository;
import com.xb.cinstar.service.ITicketRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TicketRelationService implements ITicketRelationService {
    @Autowired
    private ISeatRespository iSeatRespository;
    @Autowired
    private ITicketRespository iTicketRespository;

    @Autowired
    private IShowtimeRespository iShowtimeRespository;

    @Autowired
    private ITicketRelationRepository iTicketRelationRepository;

    public List<TicketRelationDTO> save(List<TicketRelationDTO> ticketRelationDTOs) {
        List<TicketRelation> relationModels = new ArrayList<>();
       ticketRelationDTOs.stream().forEach(ticketRelationDTO -> {
           if(!iSeatRespository.existsById(ticketRelationDTO.getSeatId()))
           {
               throw new ResourceNotFoundException("Not found seat");
           }
           if(!iShowtimeRespository.existsById(ticketRelationDTO.getShowtimeId()))
           {
               throw new ResourceNotFoundException("Not found showtime");
           }
           if(!iTicketRespository.existsById(ticketRelationDTO.getTicketId()))
           {
               throw new ResourceNotFoundException("Not found this ticket");
           }
           SeatModel seatModel = iSeatRespository.findById(ticketRelationDTO.getSeatId()).get();
           ShowTimeModel showTimeModel = iShowtimeRespository.findById(ticketRelationDTO.getShowtimeId()).get();
           TicketModel ticketModel = iTicketRespository.findById(ticketRelationDTO.getTicketId()).get();
           TicketRelation ticketRelation =  new TicketRelation();
           ticketRelation.setTicket(ticketModel);
           ticketRelation.setSeat(seatModel);
           ticketRelation.setShowtime(showTimeModel);
           relationModels.add(ticketRelation);
       });

        List<TicketRelation>ticketRelations = iTicketRelationRepository.saveAll(relationModels);
        List<TicketRelationDTO> results = new ArrayList<>();
        ticketRelations.stream().forEach(ticketRelation->{
            TicketRelationDTO result = new TicketRelationDTO();
            result.setQuantity(ticketRelation.getQuantity());
            result.setSeatId(ticketRelation.getSeat().getId());
            result.setShowtimeId(ticketRelation.getShowtime().getId());
            result.setTicketId(ticketRelation.getTicket().getId());
            result.setId(ticketRelation.getId());
            results.add(result);

        });


        return results;
    }

    @Override
    public TicketRelationDTO save(TicketRelationDTO ticketRelationDTO) {
        return null;
    }

    @Override
    public boolean delete(Long id) {
        if(iTicketRelationRepository.existsById(id))
        {
            iTicketRelationRepository.deleteById(id);
            return  true;
        }
        else
        {
            throw  new ResourceNotFoundException("Not found this Ticket ");

        }

    }

    @Override
    public boolean update(Long id) {
        return false;
    }

    public TicketRelationDTO findById(Long id) {

        Optional<TicketRelation> ticketRelation =iTicketRelationRepository.findById(id);
        if(!ticketRelation.isPresent())
        {
            throw  new ResourceNotFoundException("Not found this ticket relation");
        }

        TicketRelationDTO result = new TicketRelationDTO();
        result.setQuantity(ticketRelation.get().getQuantity());
        result.setSeatId(ticketRelation.get().getSeat().getId());
        result.setShowtimeId(ticketRelation.get().getShowtime().getId());
        result.setTicketId(ticketRelation.get().getTicket().getId());
        result.setTicketorderId(ticketRelation.get().getTicketorder().getId());


        return result;
    }
    public List<TicketRelationDTO> findAllByTicketOrder(Long ticketid) {
        List<TicketRelation> ticketRelations = iTicketRelationRepository.findAllByTicketorderId(ticketid);
       return ticketRelations.stream().map(ticketRelation -> {
            TicketRelationDTO ticketOrderDTO =new TicketRelationDTO();
            ticketOrderDTO.setQuantity(ticketRelation.getQuantity());
            ticketOrderDTO.setSeatId(ticketRelation.getSeat().getId());
            ticketOrderDTO.setShowtimeId(ticketRelation.getShowtime().getId());
            ticketOrderDTO.setTicketId(ticketRelation.getTicket().getId());
           ticketOrderDTO.setTicketorderId(ticketRelation.getTicketorder().getId());
           ticketOrderDTO.setId(ticketRelation.getId());
            return  ticketOrderDTO;
    }).collect(Collectors.toList());

    }
    @Override
    public List<TicketRelationDTO> findAll() {
        return null;
    }

    @Override
    public List<TicketRelationDTO> findAll(Pageable pageable) {
        return null;
    }



}
