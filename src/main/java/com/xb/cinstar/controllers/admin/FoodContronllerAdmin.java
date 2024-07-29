package com.xb.cinstar.controllers.admin;

import com.xb.cinstar.dto.FoodDTO;
import com.xb.cinstar.service.impl.SeatService;
import com.xb.cinstar.service.impl.FoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/test/food")
public class FoodContronllerAdmin {


    @Autowired
    private FoodService foodService;
    @Autowired private SeatService seatService;
    @PostMapping
    public ResponseEntity<?> save(@RequestBody FoodDTO foodDTO)
    {
        FoodDTO result =  foodService.save(foodDTO);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Long id, @RequestBody  FoodDTO foodDTO)
    {
        foodDTO.setId(id);
        FoodDTO result =  foodService.save(foodDTO);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    @GetMapping( )
    public ResponseEntity<?> getAll()
    {
        List<FoodDTO> foodDTOS = foodService.findAll();

        return new ResponseEntity<>(foodDTOS, HttpStatus.OK);
    }
    @GetMapping("/detail/{id}")
    public ResponseEntity<?> getDetail(@PathVariable("id") Long id)
    {

        FoodDTO foodDTO = foodService.findById(id);

        return new ResponseEntity<>(foodDTO, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id)
    {
        if(foodService.delete(id))
        {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_GATEWAY);
    }
}
