package com.xb.cinstar.controllers.admin;

import com.xb.cinstar.dto.ScreenDTO;
import com.xb.cinstar.service.impl.SeatService;
import com.xb.cinstar.service.impl.ScreenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/admin/screen")
public class ScreenControllerAdmin {
    @Autowired
    private ScreenService screenService;
    @Autowired private SeatService seatService;
    @PostMapping
    public ResponseEntity<?> save(@RequestBody ScreenDTO screenDTO)
    {
        List<Long> list = new ArrayList<>();
        for (Long i = 8437L; i < 8536L; i++){
            list.add(i);
        }
        screenDTO.setSeatIds(list);
        ScreenDTO result =  screenService.save(screenDTO);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable Long id)
    {
        ScreenDTO screenDTO = new ScreenDTO();
        List<Long> list = new ArrayList<>();
        for (Long i = 8437L; i < 8536L; i++){
            list.add(i);
        }
        screenDTO.setSeatIds(list);
        screenDTO.setId(id);
        ScreenDTO result =  screenService.update(screenDTO);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id)
    {
        if(screenService.delete(id))
        {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_GATEWAY);
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> findAllByTheaterId(@PathVariable("id") Long id)
    {

        List<ScreenDTO> result = screenService.findAllByTheaterId(id);
        return ResponseEntity.ok(result);
    }
}
