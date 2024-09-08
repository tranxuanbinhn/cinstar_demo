package com.xb.cinstar.controllers.user;

import com.xb.cinstar.dto.OrderDTO;
import com.xb.cinstar.dto.PaymentDTO;
import com.xb.cinstar.service.impl.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("${allowed.origin}")
@RestController
@RequestMapping("/api/user/payment")
public class PaymentControllerUser {
    @Autowired private PaymentService paymentService;
    @PostMapping()
    public ResponseEntity<?> save(@RequestBody PaymentDTO paymentDTO)
    {

        PaymentDTO ticketOrderDTO1 = paymentService.save(paymentDTO);

        return new ResponseEntity<>(ticketOrderDTO1, HttpStatus.OK);
    }
}
