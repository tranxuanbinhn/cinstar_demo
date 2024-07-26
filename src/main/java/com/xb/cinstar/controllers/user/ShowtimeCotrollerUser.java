package com.xb.cinstar.controllers.user;


import com.xb.cinstar.dto.PromotionDTO;
import com.xb.cinstar.dto.ShowTimeDTO;
import com.xb.cinstar.service.impl.PromotionService;
import com.xb.cinstar.service.impl.ShowTimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
@RequestMapping("/test/user/showtime")
@RestController
public class ShowtimeCotrollerUser {
    @Autowired
    private ShowTimeService showTimeService;
    @GetMapping()
    public ResponseEntity<?> getAll(@RequestBody ShowTimeDTO showTimeDTO)
    {
        List<ShowTimeDTO> showTimeDTOS = new ArrayList<>();
        if(showTimeDTO.getTheaterId()==null)
        {
            showTimeDTOS = showTimeService.findAll(showTimeDTO.getMovieId(), showTimeDTO.getDate());
        }
        else {
             showTimeDTOS = showTimeService.findAll(showTimeDTO.getDate(), showTimeDTO.getMovieId(), showTimeDTO.getTheaterId());

        }
        return new ResponseEntity<>(showTimeDTOS, HttpStatus.OK);
    }
    @GetMapping("/getbeymovie/{id}")
    public ResponseEntity<?> getAllByMovieId(@PathVariable("id") Long id )
    {
        List<ShowTimeDTO> showTimeDTOS = showTimeService.findByMovieId(id);

        return new ResponseEntity<>(showTimeDTOS, HttpStatus.OK);
    }
}
