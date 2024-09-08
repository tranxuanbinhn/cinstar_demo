package com.xb.cinstar.controllers.user;

import com.xb.cinstar.dto.CustomerDTO;
import com.xb.cinstar.dto.OrderDTO;
import com.xb.cinstar.service.impl.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("${allowed.origin}")
@RestController
@RequestMapping("/api/user/customer")
public class CustomerController {
    @Autowired private CustomerService customerService;

    @PostMapping()
    public ResponseEntity<?> save(@RequestBody CustomerDTO customerDTO)
    {

        CustomerDTO result = customerService.save(customerDTO);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    @GetMapping("/getbyid/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id)
    {

        CustomerDTO result = customerService.findOne(id);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
