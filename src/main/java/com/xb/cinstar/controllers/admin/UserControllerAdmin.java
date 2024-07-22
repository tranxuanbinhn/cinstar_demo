package com.xb.cinstar.controllers.admin;

import com.xb.cinstar.dto.UserDTO;
import com.xb.cinstar.payload.response.PageResponse;
import com.xb.cinstar.service.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/test/user")
public class UserControllerAdmin {

    @Autowired
    private UserService userService;
    @PostMapping
    public ResponseEntity<?> save(@RequestBody  UserDTO userDTO)
    {
        UserDTO result =  userService.save(userDTO);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Long id,@RequestBody  UserDTO userDTO)
    {
        userDTO.setId(id);
        UserDTO result =  userService.save(userDTO);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    @GetMapping( )
    public ResponseEntity<?> getAll(@RequestParam("page") int page,@RequestParam("limit") int limit)
    {
        Pageable pageable = PageRequest.of(page-1, limit);
        List<UserDTO> userDTOS = userService.findAll(pageable);
        PageResponse result = new PageResponse();
        result.setResults(userDTOS);
        result.setPage(page);
        result.setLimit(limit);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    @GetMapping("/detail/{id}")
    public ResponseEntity<?> getDetail(@PathVariable("id") Long id)
    {

        UserDTO userDTO = userService.findById(id);

        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id)
    {
        if(userService.delete(id))
        {
            return new ResponseEntity<>(HttpStatus.OK);
        }


        return new ResponseEntity<>(HttpStatus.BAD_GATEWAY);
    }

}
