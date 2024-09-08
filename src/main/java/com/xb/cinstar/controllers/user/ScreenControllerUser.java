package com.xb.cinstar.controllers.user;

import com.xb.cinstar.dto.FoodDTO;
import com.xb.cinstar.dto.ScreenDTO;
import com.xb.cinstar.service.impl.FoodService;
import com.xb.cinstar.service.impl.ScreenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("${allowed.origin}")
@RestController
@RequestMapping("/api/user/screen")
public class ScreenControllerUser {
    @Autowired
    private ScreenService screenService;



    @GetMapping("/getbyid/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id)
    {

        ScreenDTO result = screenService.findById(id);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }






}
