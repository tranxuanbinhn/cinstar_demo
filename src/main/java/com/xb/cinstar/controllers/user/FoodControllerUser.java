package com.xb.cinstar.controllers.user;

import com.xb.cinstar.dto.FoodDTO;
import com.xb.cinstar.dto.TicketDTO;
import com.xb.cinstar.service.impl.FoodService;
import com.xb.cinstar.service.impl.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("${allowed.origin}")
@RestController
@RequestMapping("/api/user/food")
public class FoodControllerUser {
    @Autowired
    private FoodService foodService;


    @GetMapping("/getall")
    public ResponseEntity<?> findAll()
    {

        List<FoodDTO> result = foodService.findAll();

        return new ResponseEntity<>(result, HttpStatus.OK);
    }






}
