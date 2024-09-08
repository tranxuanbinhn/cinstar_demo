package com.xb.cinstar.controllers.admin;

import com.xb.cinstar.dto.CustomerDTO;
import com.xb.cinstar.service.impl.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("${allowed.origin}")
@RestController
@RequestMapping("/api/admin/customer")
public class CustomerControllerAdmin {
    @Autowired private CustomerService customerService;

    @GetMapping("/getbyid/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id)
    {

        CustomerDTO result = customerService.findOne(id);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
