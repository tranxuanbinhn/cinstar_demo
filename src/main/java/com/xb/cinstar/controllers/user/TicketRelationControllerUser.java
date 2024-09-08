package com.xb.cinstar.controllers.user;

import com.xb.cinstar.dto.TicketRelationDTO;
import com.xb.cinstar.service.impl.TicketRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin("${allowed.origin}")
@RestController
@RequestMapping("/api/user/ticketrelation")
public class TicketRelationControllerUser {
    @Autowired private TicketRelationService ticketRelationService;
    @PostMapping
    public ResponseEntity<?> save(@RequestBody List<TicketRelationDTO> ticketDTOs)
    {


        List<TicketRelationDTO> result =  ticketRelationService.save(ticketDTOs);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }


    public ResponseEntity<?> getById(@PathVariable("id") Long id)
    {
        TicketRelationDTO result =  ticketRelationService.findById(id);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    @GetMapping("/findbyorderticketid/{id}")
    public ResponseEntity<?> getByOrderTicketId(@PathVariable("id") Long id)
    {
        List<TicketRelationDTO> result =  ticketRelationService.findAllByTicketOrder(id);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
