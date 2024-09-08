package com.xb.cinstar.controllers.user;

import com.xb.cinstar.dto.OrderDTO;
import com.xb.cinstar.dto.TheaterDTO;
import com.xb.cinstar.dto.TicketOrderDTO;

import com.xb.cinstar.dto.TicketResponse;
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
@RequestMapping("/api/user/order")
@RestController
public class OrderControllerUser {
    @Autowired
    private TicketOrderService ticketOrderService;
    @Autowired private OrderService orderService;



    @PostMapping()
    public ResponseEntity<?> save(@RequestBody OrderDTO orderDTO)
    {

        OrderDTO ticketOrderDTO1 = orderService.save(orderDTO);

        return new ResponseEntity<>(ticketOrderDTO1, HttpStatus.OK);
    }
    @GetMapping("/ticket/{id}")
    public ResponseEntity<?> getTicketByOrder(@PathVariable Long id)
    {

        TicketResponse ticketOrderDTO1 = orderService.findTicketResponseByOrderid(id);

        return new ResponseEntity<>(ticketOrderDTO1, HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id)
    {

        if(orderService.delete(id))
        {
            return new ResponseEntity<>("Success", HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>("False", HttpStatus.BAD_REQUEST);
        }


    }
    @GetMapping("/getall")
    public ResponseEntity<?> getAll(@RequestParam("page")int page, @RequestParam("limit")int limit)
    {
        Pageable pageable = PageRequest.of(page-1, limit);
        List<OrderDTO> ticketOrderDTO1 = orderService.findAll(pageable);

        return new ResponseEntity<>(ticketOrderDTO1, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<?> save(@RequestBody TicketOrderDTO  ticketOrderDTO)
    {

        TicketOrderDTO ticketOrderDTO1 = ticketOrderService.save(
                ticketOrderDTO
        );

        return new ResponseEntity<>(ticketOrderDTO1, HttpStatus.OK);
    }

}
