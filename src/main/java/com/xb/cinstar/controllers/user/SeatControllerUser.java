package com.xb.cinstar.controllers.user;

import com.xb.cinstar.dto.SeatDTO;
import com.xb.cinstar.dto.TheaterDTO;
import com.xb.cinstar.service.impl.SeatService;
import com.xb.cinstar.service.impl.TheaterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("${allowed.origin}")
@RequestMapping("/api/user/seat")
@RestController
public class SeatControllerUser {

    @Autowired
    private SeatService seatService;
    @GetMapping("/findbyscreen/{id}")
    public ResponseEntity<?> getAll(@PathVariable Long id)
    {

        List<SeatDTO> result = seatService.findByScreenId(id);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    @PostMapping()
    public ResponseEntity<?> save(@RequestBody  SeatDTO seatDTO)
    {

        SeatDTO result = seatService.save(seatDTO);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    @PostMapping("/saveall")
    public void saveall()
    {

       seatService.save();


    }

    @DeleteMapping()
    public void delete(@RequestBody  Long[] ids)
    {

   seatService.delete(ids);


    }
}
