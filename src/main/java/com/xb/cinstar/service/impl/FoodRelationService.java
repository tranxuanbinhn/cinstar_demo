package com.xb.cinstar.service.impl;

import com.xb.cinstar.dto.FoodRelationDTO;
import com.xb.cinstar.exception.ResourceNotFoundException;
import com.xb.cinstar.models.*;
import com.xb.cinstar.repository.*;
import com.xb.cinstar.service.IFoodRelationService;
import com.xb.cinstar.service.ITicketRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FoodRelationService implements IFoodRelationService {


    @Autowired
    private IFoodRelationRepository ifoodrelation;

    @Autowired
    private IFoodRespository foodRespository;


    public List<FoodRelationDTO> save(List<FoodRelationDTO> foodRelationDTOs) {
        List<FoodRelation> foodRelations = new ArrayList<>();
        foodRelationDTOs.stream().forEach(foodRelationDTO-> {

            if(!foodRespository.existsById(foodRelationDTO.getFoodId()))
            {
                throw new ResourceNotFoundException("Not found this food");
            }

            FoodModel foodModel = foodRespository.findById(foodRelationDTO.getFoodId()).get();
            FoodRelation foodRelation = new FoodRelation();
            foodRelation.setFood(foodModel);
            foodRelation.setQuantity(foodRelationDTO.getQuantity());
            foodRelations.add(foodRelation);

        });

        List<FoodRelation> result = ifoodrelation.saveAll(foodRelations);

        List<FoodRelationDTO> results = new ArrayList<>();
        result.stream().forEach(foodRelation -> {
            FoodRelationDTO foodRelationDTO = new FoodRelationDTO();
            foodRelationDTO.setFoodId(foodRelation.getFood().getId());
            foodRelationDTO.setQuantity(foodRelation.getQuantity());
            foodRelationDTO.setId(foodRelation.getId());
            results.add(foodRelationDTO);
        });

        return results;
    }

    @Override
    public FoodRelationDTO save(FoodRelationDTO foodRelationDTO) {
        return null;
    }

    @Override
    public boolean delete(Long id) {
        if(ifoodrelation.existsById(id))
        {
            ifoodrelation.deleteById(id);
            return  true;
        }
        else
        {
            throw  new ResourceNotFoundException("Not found this Foodrealation ");

        }

    }

    @Override
    public boolean update(Long id) {
        return false;
    }

    public FoodRelationDTO findById(Long id) {




        Optional<FoodRelation> foodRelation = ifoodrelation.findById(id);
        if(!foodRelation.isPresent())
        {
            throw  new ResourceNotFoundException("Not found this food");
        }


        FoodRelationDTO result = new FoodRelationDTO();
        result.setFoodId(foodRelation.get().getFood().getId());
        result.setId(foodRelation.get().getId());
        result.setQuantity(foodRelation.get().getQuantity());
        return result;
    }
    public List<FoodRelationDTO> findAllByTicketOrder(Long ticketid) {
        List<FoodRelation> ticketRelations = ifoodrelation.findAllByTicketorderId(ticketid);
       return ticketRelations.stream().map(ticketRelation -> {
            FoodRelationDTO foodRelationDTO =new FoodRelationDTO();
            foodRelationDTO.setQuantity(ticketRelation.getQuantity());

           foodRelationDTO.setTicketorderId(ticketRelation.getTicketorder().getId());
           foodRelationDTO.setId(ticketRelation.getId());
            return  foodRelationDTO;
    }).collect(Collectors.toList());

    }
    @Override
    public List<FoodRelationDTO> findAll() {
        return null;
    }

    @Override
    public List<FoodRelationDTO> findAll(Pageable pageable) {
        return null;
    }



}
