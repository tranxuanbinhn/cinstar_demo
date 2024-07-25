package com.xb.cinstar.controllers.admin;

import com.xb.cinstar.dto.PromotionDTO;
import com.xb.cinstar.payload.response.PageResponse;
import com.xb.cinstar.service.impl.PromotionService;
import com.xb.cinstar.service.impl.TheaterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/test/promotion")
public class PromotionControllerAdmin {
    @Autowired
    private PromotionService promotionService;
    @PostMapping
    public ResponseEntity<?> save(@RequestBody PromotionDTO promotionDTO)
    {
        PromotionDTO result =  promotionService.save(promotionDTO);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Long id,@RequestBody  PromotionDTO promotionDTO)
    {
        promotionDTO.setId(id);
        PromotionDTO result =  promotionService.save(promotionDTO);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    @GetMapping( )
    public ResponseEntity<?> getAll()
    {

        List<PromotionDTO> promotionDTOS = promotionService.findAll();
      

        return new ResponseEntity<>(promotionDTOS, HttpStatus.OK);
    }
    @GetMapping("/detail/{id}")
    public ResponseEntity<?> getDetail(@PathVariable("id") Long id)
    {

        PromotionDTO promotionDTO = promotionService.findById(id);

        return new ResponseEntity<>(promotionDTO, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id)
    {
        if(promotionService.delete(id))
        {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_GATEWAY);
    }


}
