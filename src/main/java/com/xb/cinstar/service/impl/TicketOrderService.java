package com.xb.cinstar.service.impl;

import com.xb.cinstar.dto.TicketOrderDTO;
import com.xb.cinstar.exception.ResourceNotFoundException;
import com.xb.cinstar.models.*;
import com.xb.cinstar.repository.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TicketOrderService {
    @Autowired private ITicketOrderRespository ticketOrderRespository;
    @Autowired private ITicketRelationRepository iTicketRelationRepository;
    @Autowired private IFoodRelationRepository foodRespository;

    @Autowired private ModelMapper mapper;

    public TicketOrderDTO save(TicketOrderDTO ticketOrderDTO)
    {
        TicketOrderModel ticketOrderModel = new TicketOrderModel();
        ticketOrderModel = ticketOrderRespository.save(ticketOrderModel);
        List<TicketRelation> ticketRelations = new ArrayList<>();
        List<FoodRelation> foodModels = new ArrayList<>();

        for (Long id: ticketOrderDTO.getTicketRelationIds()){
            TicketRelation ticketRelation = iTicketRelationRepository.findById(id)
                    .orElseThrow(()-> new ResourceNotFoundException("Not found this ticket model"));
            ticketRelation.setTicketorder(ticketOrderModel);
            ticketRelations.add(ticketRelation);
        };

        if(ticketOrderDTO.getFoodIds()!=null)
        {
            for (Long id: ticketOrderDTO.getFoodIds()){
                FoodRelation foodModel = foodRespository.findById(id)
                        .orElseThrow(()-> new ResourceNotFoundException("Not found this ticket model"));
                foodModel.setTicketorder(ticketOrderModel);
                foodModels.add(foodModel);
            };
        }
        iTicketRelationRepository.saveAll(ticketRelations);
        foodRespository.saveAll(foodModels);
        TicketOrderDTO result = new TicketOrderDTO();
        result = mapper.map(ticketOrderModel, TicketOrderDTO.class);
        result.setTicketRelationIds(ticketRelations.stream().map(BaseEntity::getId).collect(Collectors.toList()));
        result.setFoodIds(foodModels.stream().map(BaseEntity::getId).collect(Collectors.toList()));

        return  result;
    }
    public TicketOrderDTO findByOrderId(Long orderId)
    {
        TicketOrderModel ticketOrderModel = ticketOrderRespository.findByOrderId(orderId);
        TicketOrderDTO result = new TicketOrderDTO();
        result = mapper.map(ticketOrderModel, TicketOrderDTO.class);
        result.setTicketRelationIds(ticketOrderModel.getTickets().stream().map(BaseEntity::getId).collect(Collectors.toList()));
        result.setFoodIds(ticketOrderModel.getFoods().stream().map(BaseEntity::getId).collect(Collectors.toList()));
       return  result;
    }

    public boolean delete(Long id) {
        try{
            ticketOrderRespository.deleteById(id);
            return  true;
        }
        catch (ResourceNotFoundException e)
        {
            throw new ResourceNotFoundException("Not found this Theater");

        }

    }
}
