package com.xb.cinstar.controllers.user;

import com.xb.cinstar.dto.OrderDTO;
import com.xb.cinstar.dto.TicketOrderDTO;
import com.xb.cinstar.service.impl.OrderService;
import com.xb.cinstar.service.impl.TicketOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("${allowed.origin}")
@RequestMapping("/api/user/ticketorder")
@RestController
public class TicketOrderControllerUser {
    @Autowired
    private TicketOrderService ticketOrderService;







    @PostMapping()
    public ResponseEntity<?> save(@RequestBody TicketOrderDTO  ticketOrderDTO)
    {

        TicketOrderDTO ticketOrderDTO1 = ticketOrderService.save(
                ticketOrderDTO
        );

        return new ResponseEntity<>(ticketOrderDTO1, HttpStatus.OK);
    }

}
