package com.xb.cinstar.service.impl;

import com.xb.cinstar.dto.FoodDTO;
import com.xb.cinstar.exception.ResourceNotFoundException;
import com.xb.cinstar.models.FoodModel;
import com.xb.cinstar.models.SeatModel;
import com.xb.cinstar.models.ShowTimeModel;
import com.xb.cinstar.models.FoodModel;
import com.xb.cinstar.repository.IFoodRespository;
import com.xb.cinstar.repository.ISeatRespository;
import com.xb.cinstar.repository.IShowtimeRespository;
import com.xb.cinstar.repository.ITicketRespository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FoodService {
    @Autowired private IFoodRespository foodRepository;


    @Autowired private ModelMapper mapper;

    public FoodDTO save(FoodDTO foodDTO)
    {
        try{
            FoodModel foodModel =  new FoodModel();
            if(foodDTO.getId()!=null)
            {
                foodModel = foodRepository.findById(foodDTO.getId()).orElseThrow(()-> new ResourceNotFoundException("Not found this food"));
            }
             foodModel = mapper.map(foodDTO, FoodModel.class);
            foodModel = foodRepository.save(foodModel);
            return mapper.map(foodModel, FoodDTO.class);
 }
        catch (ResourceNotFoundException e)
        {
            throw new  ResourceNotFoundException("Not found user");
        }
    }


    public boolean delete(Long id) {
        try{
            foodRepository.deleteById(id);
            return  true;
        }
        catch (ResourceNotFoundException e)
        {
            throw new ResourceNotFoundException("Not found this Theater");

        }

    }





    public List<FoodDTO> findAll()
    {
        List<FoodModel> foods = foodRepository.findAll();
        List<FoodDTO> result = new ArrayList<>();
        foods.stream().forEach(theater->{
            FoodDTO foodDTO = new FoodDTO();
            foodDTO = mapper.map(theater, FoodDTO.class);
            result.add(foodDTO);
                }
        );
        return result;
    }

    public FoodDTO findById(Long id)
    {
        FoodModel foodModel = foodRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Not found this food"));
        return mapper.map(foodModel, FoodDTO.class);
    }



}
