package com.xb.cinstar.controllers.user;

import com.xb.cinstar.dto.PromotionDTO;
import com.xb.cinstar.service.impl.PromotionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RequestMapping("/test/user/promotion")
@RestController
public class PromotionControllerUser {
    @Autowired private PromotionService promotionService;
    @GetMapping( )
    public ResponseEntity<?> getAll()
    {

        List<PromotionDTO> promotionDTOS = promotionService.findAll();


        return new ResponseEntity<>(promotionDTOS, HttpStatus.OK);
    }
    @GetMapping("/getbyuser/{id}")
    public ResponseEntity<?> getByUserId(@PathVariable("id") Long id)
    {

        List<PromotionDTO> promotionDTOS = promotionService.findByUserId(id);


        return new ResponseEntity<>(promotionDTOS, HttpStatus.OK);
    }
}
