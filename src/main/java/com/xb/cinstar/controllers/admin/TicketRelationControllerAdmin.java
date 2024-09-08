package com.xb.cinstar.controllers.admin;

import com.xb.cinstar.dto.TicketDTO;
import com.xb.cinstar.dto.TicketRelationDTO;
import com.xb.cinstar.service.impl.TicketRelationService;
import com.xb.cinstar.service.impl.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/test/admin/ticketrelation")
public class TicketRelationControllerAdmin {
    @Autowired private TicketRelationService ticketRelationService;
    @PostMapping
    public ResponseEntity<?> save(@RequestBody TicketRelationDTO ticketDTO)
    {


        TicketRelationDTO result =  ticketRelationService.save(ticketDTO);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id)
    {
        if(ticketRelationService.delete(id))
        {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_GATEWAY);
    }
    @GetMapping("/findbyid/{id}")
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
