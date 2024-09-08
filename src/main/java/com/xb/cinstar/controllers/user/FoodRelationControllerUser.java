package com.xb.cinstar.controllers.user;

import com.xb.cinstar.dto.FoodRelationDTO;
import com.xb.cinstar.service.impl.FoodRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin("${allowed.origin}")
@RestController
@RequestMapping("/api/user/foodrelation")
public class FoodRelationControllerUser {
    @Autowired private FoodRelationService foodRelationService;
    @PostMapping
    public ResponseEntity<?> save(@RequestBody List<FoodRelationDTO> foodRelationDTOs)
    {


        List<FoodRelationDTO> result =  foodRelationService.save(foodRelationDTOs);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }


    @GetMapping("/findbyid/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") Long id)
    {
        FoodRelationDTO result =  foodRelationService.findById(id);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    @GetMapping("/findbyorderticketid/{id}")
    public ResponseEntity<?> getByOrderTicketId(@PathVariable("id") Long id)
    {
        List<FoodRelationDTO> result =  foodRelationService.findAllByTicketOrder(id);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
