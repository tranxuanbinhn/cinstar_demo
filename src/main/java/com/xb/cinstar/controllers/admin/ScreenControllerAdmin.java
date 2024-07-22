package com.xb.cinstar.controllers.admin;

import com.xb.cinstar.dto.ScreenDTO;
import com.xb.cinstar.dto.TheaterDTO;
import com.xb.cinstar.service.SeatService;
import com.xb.cinstar.service.impl.ScreenService;
import com.xb.cinstar.service.impl.TheaterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/test/screen")
public class ScreenControllerAdmin {
    @Autowired
    private ScreenService screenService;
    @Autowired private SeatService seatService;
    @PostMapping
    public ResponseEntity<?> save(@RequestBody ScreenDTO screenDTO)
    {
        List<Long> list = new ArrayList<>();
        for (Long i = 8226L; i < 8436; i++){
            list.add(i);
        }
        screenDTO.setSeatIds(list);
        ScreenDTO result =  screenService.save(screenDTO);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
