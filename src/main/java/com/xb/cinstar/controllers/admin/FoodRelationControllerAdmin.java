package com.xb.cinstar.controllers.admin;

import com.xb.cinstar.dto.FoodRelationDTO;
import com.xb.cinstar.service.impl.FoodRelationService;
import com.xb.cinstar.service.impl.TicketRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/test/admin/foodrelation")
public class FoodRelationControllerAdmin {
    @Autowired private FoodRelationService foodRelationService;
    @PostMapping
    public ResponseEntity<?> save(@RequestBody FoodRelationDTO foodRelationDTO)
    {


        FoodRelationDTO result =  foodRelationService.save(foodRelationDTO);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id)
    {
        if(foodRelationService.delete(id))
        {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_GATEWAY);
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
