package com.xb.cinstar.controllers.user;

import com.xb.cinstar.dto.MovieDTO;
import com.xb.cinstar.dto.MovieTheaterDTO;
import com.xb.cinstar.dto.TheaterDTO;
import com.xb.cinstar.service.impl.MovieService;
import com.xb.cinstar.service.impl.TheaterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/test/user/theater")
public class TheaterControllerUser {
    @Autowired
    private TheaterService theaterService;
    @GetMapping("/findall")
    public ResponseEntity<?> getAll()
    {

        List<TheaterDTO> result = theaterService.findAll();

        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    @GetMapping("/detail/{id}")
    public ResponseEntity<?> getDetail(@PathVariable("id") Long id)
    {

        TheaterDTO result = theaterService.findById(id);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    @GetMapping("/findallbycity")
    public ResponseEntity<?> getAllBuCity(@RequestParam("city") String city)
    {

        List<TheaterDTO> result = theaterService.findTheaterByCity(city);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }





}
