package com.xb.cinstar.controllers.admin;

import com.xb.cinstar.dto.TicketDTO;
import com.xb.cinstar.service.impl.ScreenService;
import com.xb.cinstar.service.impl.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
@RestController
@RequestMapping("/test/admin/ticket")
public class TicketControllerAdmin {
    @Autowired private TicketService ticketService;
    @PostMapping
    public ResponseEntity<?> save(@RequestBody TicketDTO ticketDTO)
    {


        TicketDTO result =  ticketService.save(ticketDTO);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody TicketDTO ticketDTO)
    {

        ticketDTO.setId(id);
        TicketDTO result =  ticketService.save(ticketDTO);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    @GetMapping()
    public ResponseEntity<?> getAll()
    {


        List<TicketDTO> result =  ticketService.findAll();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }



    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id)
    {
        if(ticketService.delete(id))
        {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_GATEWAY);
    }
}
