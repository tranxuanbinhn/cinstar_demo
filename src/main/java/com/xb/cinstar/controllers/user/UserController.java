package com.xb.cinstar.controllers.user;

import com.xb.cinstar.dto.CustomerDTO;
import com.xb.cinstar.dto.OrderDTO;
import com.xb.cinstar.dto.TheaterDTO;
import com.xb.cinstar.dto.UserDTO;
import com.xb.cinstar.models.UserModel;
import com.xb.cinstar.service.impl.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@CrossOrigin("${allowed.origin}")
@RestController
    @RequestMapping("/api/information")
public class UserController {
    @Autowired private UserService userService;
    @Autowired private TheaterService theaterService;
    @Autowired private OrderService orderService;

    @PreAuthorize("#userName==authentication.principal")
    @GetMapping("/getbyid/{userName}")
    public ResponseEntity<?> getById(@PathVariable String userName)
    {


        UserDTO result = userService.findById(userName);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
     @PreAuthorize("#userName==authentication.principal")
    @PutMapping("/update/{userName}")
    public ResponseEntity<?> update(@RequestBody UserDTO userDTO,  @PathVariable String userName)
    {
        userDTO.setUserName(userName);
        UserDTO result = userService.update(userDTO);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
     @PreAuthorize("#userName==authentication.principal")
    @PutMapping("/changepassword/{userName}")
    public ResponseEntity<?> changepassword(@RequestBody UserDTO userDTO, @PathVariable String userName)
    {
        userDTO.setUserName(userName);
        UserDTO result = userService.changePassword(userDTO);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    @PreAuthorize("#userName==authentication.principal")
    @GetMapping("/gettheaterbyid/{userName}/{id}")
    public ResponseEntity<?> gettheater(@PathVariable String userName,@PathVariable Long id)
    {
       TheaterDTO theaterDTO = theaterService.findOneByOrderId(id);
        return new ResponseEntity<>(theaterDTO, HttpStatus.OK);
    }
    @PreAuthorize("#userName==authentication.principal")
    @GetMapping("/getallorders/{userName}")
    public ResponseEntity<?> getAllOrders(@PathVariable String userName)
    {
        List<OrderDTO> result = orderService.findAllByUsername(userName);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }


}
