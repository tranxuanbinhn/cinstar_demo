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

@RestController
@RequestMapping("/test/user/ticket")
public class TickerControllerUser {
    @Autowired
    private TicketService ticketService;
    @PostMapping("/add")
    public ResponseEntity<?> save(@RequestBody TicketDTO ticketDTO)
    {

        TicketDTO result = ticketService.save(ticketDTO);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }






}
