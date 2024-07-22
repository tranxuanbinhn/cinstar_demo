package com.xb.cinstar.service.impl;

import com.xb.cinstar.dto.PromotionDTO;
import com.xb.cinstar.exception.ResourceNotFoundException;
import com.xb.cinstar.models.PromotionModel;
import com.xb.cinstar.repository.IPromotionRespository;
import com.xb.cinstar.repository.ITheaterRespository;
import com.xb.cinstar.service.IPromotionService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class PromotionService implements IPromotionService {
    @Autowired
    private IPromotionRespository promotionService;

    @Autowired private ModelMapper mapper;

    public PromotionDTO save(PromotionDTO promotionDTO)
    {
        try{
            PromotionModel promotionModel ;
            if(promotionDTO.getId()!=null)
            {
                promotionModel =promotionService.findById(promotionDTO.getId()).get();

            }


            promotionModel = mapper.map(promotionDTO, PromotionModel.class);
            promotionModel = promotionService.save(promotionModel);
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
            promotionService.deleteById(id);
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
    public List<PromotionDTO> findAll() {
        return null;
    }

    public List<PromotionDTO> findAll(Pageable pageable)
    {
        Page<PromotionModel> theaters = promotionService.findAll(pageable);
        List<PromotionDTO> result = new ArrayList<>();
        theaters.getContent().stream().forEach(theater->{
                    PromotionDTO promotionDTO = new PromotionDTO();
                    promotionDTO = mapper.map(theater, PromotionDTO.class);
                    result.add(promotionDTO);
                }
        );
        return result;
    }

    public PromotionDTO findById(Long id){
        if(!promotionService.existsById(id))
        {
            throw  new ResourceNotFoundException("Not found Promotion");
        }
        return mapper.map(promotionService.findById(id).get(),PromotionDTO.class);
    }


}
