package com.xb.cinstar.service.impl;

import com.xb.cinstar.dto.PromotionDTO;
import com.xb.cinstar.exception.ResourceNotFoundException;
import com.xb.cinstar.models.PromotionModel;
import com.xb.cinstar.models.ShowTimeModel;
import com.xb.cinstar.models.UserModel;
import com.xb.cinstar.repository.IPromotionRespository;
import com.xb.cinstar.repository.IShowtimeRespository;
import com.xb.cinstar.repository.ITheaterRespository;
import com.xb.cinstar.repository.IUserRepository;
import com.xb.cinstar.service.IPromotionService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
@Service
public class PromotionService implements IPromotionService {
    @Autowired
    private IPromotionRespository promotionRepository;
    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private IShowtimeRespository showtimeRespository;

    @Autowired private ModelMapper mapper;

    public PromotionDTO save(PromotionDTO promotionDTO)
    {
        try{
            PromotionModel promotionModel ;
            if(promotionDTO.getId()!=null)
            {
                promotionModel =promotionRepository.findById(promotionDTO.getId()).get();

            }


            promotionModel = mapper.map(promotionDTO, PromotionModel.class);
            promotionModel = promotionRepository.save(promotionModel);
            List<PromotionModel> promotionModels = new ArrayList<>();
            promotionModels.add(promotionModel);
            List<UserModel> userModels = userRepository.findAll();
            userModels.stream().forEach(userModel->{
                userModel.setPromotions(promotionModels);
            });
            userRepository.saveAll(userModels);
            return mapper.map(promotionModel, PromotionDTO.class);
        }
        catch (ResourceNotFoundException e)
        {
            throw new  ResourceNotFoundException("Not found promotion");
        }
    }

    @Override
    public boolean delete(Long id) {
        try{
            promotionRepository.deleteById(id);
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
    public List<PromotionDTO> findAll()
    {
        List<PromotionModel> theaters = promotionRepository.findAll();
        List<PromotionDTO> result = new ArrayList<>();
        theaters.stream().forEach(theater->{
                    PromotionDTO promotionDTO = new PromotionDTO();
                    promotionDTO = mapper.map(theater, PromotionDTO.class);
                    result.add(promotionDTO);
                }
        );
        return result;
    }

    @Override
    public List<PromotionDTO> findAll(Pageable pageable) {
        return null;
    }

    public PromotionDTO findById(Long id){
        if(!promotionRepository.existsById(id))
        {
            throw  new ResourceNotFoundException("Not found Promotion");
        }
        return mapper.map(promotionRepository.findById(id).get(),PromotionDTO.class);
    }

    public List<PromotionDTO> findByUserId(Long id){
        List<PromotionModel> promotionModels = promotionRepository.findAllByUserId(id);
        List<PromotionDTO> result = new ArrayList<>();
        promotionModels.stream().forEach(promotionModel -> {

            PromotionDTO  promotionDTO = mapper.map(promotionModel,PromotionDTO.class);
            result.add(promotionDTO);
        });
        return result;
    }

    public boolean checkPromotionValid(Long showTimeId, Long promotionId){
        ShowTimeModel showTimeModel = showtimeRespository.findById(showTimeId)
                .orElseThrow(()->new ResourceNotFoundException("Not found show time"));
        PromotionModel promotionModel =promotionRepository.findById(showTimeId)
                .orElseThrow(()->new ResourceNotFoundException("Not found promotion"));
        LocalDateTime date = showTimeModel.getDate();
        if(date.getDayOfWeek().equals(promotionModel.getDayOfWeek()))
        {
            return true;
        }
        else {
            return false;
        }

    }



}
