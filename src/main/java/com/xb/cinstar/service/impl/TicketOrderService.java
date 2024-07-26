package com.xb.cinstar.service.impl;

import com.xb.cinstar.dto.TicketOrderDTO;
import com.xb.cinstar.exception.ResourceNotFoundException;
import com.xb.cinstar.models.BaseEntity;
import com.xb.cinstar.models.FoodModel;
import com.xb.cinstar.models.TicketModel;
import com.xb.cinstar.models.TicketOrderModel;
import com.xb.cinstar.repository.IFoodRespository;
import com.xb.cinstar.repository.ITicketOrderRespository;
import com.xb.cinstar.repository.ITicketRespository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TicketOrderService {
    @Autowired private ITicketOrderRespository ticketOrderRespository;
    @Autowired private ITicketRespository ticketRespository;
    @Autowired private IFoodRespository foodRespository;

    @Autowired private ModelMapper mapper;

    public TicketOrderDTO save(TicketOrderDTO ticketOrderDTO)
    {
        TicketOrderModel ticketOrderModel = new TicketOrderModel();
        ticketOrderModel = ticketOrderRespository.save(ticketOrderModel);
        List<TicketModel> ticketModels = new ArrayList<>();
        List<FoodModel> foodModels = new ArrayList<>();

        for (Long id: ticketOrderDTO.getTicketIds()){
            TicketModel ticketModel = ticketRespository.findById(id)
                    .orElseThrow(()-> new ResourceNotFoundException("Not found this ticket model"));
            ticketModel.setTicketorder(ticketOrderModel);
            ticketModels.add(ticketModel);
        };

        if(ticketOrderDTO.getFoodIds()!=null)
        {
            for (Long id: ticketOrderDTO.getFoodIds()){
                FoodModel foodModel = foodRespository.findById(id)
                        .orElseThrow(()-> new ResourceNotFoundException("Not found this ticket model"));
                foodModel.setTicketorder(ticketOrderModel);
                foodModels.add(foodModel);
            };
        }
        ticketRespository.saveAll(ticketModels);
        foodRespository.saveAll(foodModels);
        TicketOrderDTO result = new TicketOrderDTO();
        result = mapper.map(ticketOrderModel, TicketOrderDTO.class);
        result.setTicketIds(ticketModels.stream().map(BaseEntity::getId).collect(Collectors.toList()));
        result.setFoodIds(foodModels.stream().map(BaseEntity::getId).collect(Collectors.toList()));

        return  result;
    }
    public TicketOrderDTO findByOrderId(Long orderId)
    {
        TicketOrderModel ticketOrderModel = ticketOrderRespository.findByOrderId(orderId);
        TicketOrderDTO result = new TicketOrderDTO();
        result = mapper.map(ticketOrderModel, TicketOrderDTO.class);
        result.setTicketIds(ticketOrderModel.getTickets().stream().map(BaseEntity::getId).collect(Collectors.toList()));
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
