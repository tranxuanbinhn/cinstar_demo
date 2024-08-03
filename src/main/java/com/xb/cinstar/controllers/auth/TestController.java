package com.xb.cinstar.controllers.auth;

import com.xb.cinstar.repository.IUserRepository;
import com.xb.cinstar.service.impl.UserDetailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class TestController {
    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private UserDetailServiceImpl userDetailService;


    @GetMapping("/hello")
    public String hello()
{
    return  "hello";
}

    @PostMapping()
    public String hi(@RequestBody String username)
    {


        UserDetails userDetails =  userDetailService.loadUserByUsername(username);
        return  "hello"+userDetails;
    }
}
