package com.xb.cinstar.controllers.user;

import com.xb.cinstar.dto.OrderDTO;
import com.xb.cinstar.dto.TheaterDTO;
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

@RequestMapping("/test/user/order")
@RestController
public class OrderControllerUser {
    @Autowired private OrderService ticketOrderService;



    @PostMapping("/add")
    public ResponseEntity<?> save(@RequestBody OrderDTO orderDTO)
    {

        OrderDTO ticketOrderDTO1 = ticketOrderService.save(
                orderDTO
        );

        return new ResponseEntity<>(ticketOrderDTO1, HttpStatus.OK);
    }
    @GetMapping("/getall")
    public ResponseEntity<?> getAll(@RequestParam("page")int page, @RequestParam("limit")int limit)
    {
        Pageable pageable = PageRequest.of(page-1, limit);
        List<OrderDTO> ticketOrderDTO1 = ticketOrderService.findAll(pageable);

        return new ResponseEntity<>(ticketOrderDTO1, HttpStatus.OK);
    }

}
