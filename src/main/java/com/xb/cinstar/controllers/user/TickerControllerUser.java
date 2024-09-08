package com.xb.cinstar.controllers.user;

import com.xb.cinstar.dto.TheaterDTO;
import com.xb.cinstar.dto.TicketDTO;
import com.xb.cinstar.service.impl.TheaterService;
import com.xb.cinstar.service.impl.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin("${allowed.origin}")
@RestController
@RequestMapping("/api/user/ticket")
public class TickerControllerUser {
    @Autowired
    private TicketService ticketService;


    @GetMapping("/getall")
    public ResponseEntity<?> findAll()
    {

        List<TicketDTO> result = ticketService.findAll();

        return new ResponseEntity<>(result, HttpStatus.OK);
    }






}
