package com.xb.cinstar.controllers.admin;

import com.xb.cinstar.dto.ShowTimeDTO;
import com.xb.cinstar.payload.response.PageResponse;
import com.xb.cinstar.service.impl.ShowTimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/test/showtime")
public class ShowtimeControllerAdmin {

    @Autowired private ShowTimeService showtimeService;
    @PostMapping
    public ResponseEntity<?> save(@RequestBody ShowTimeDTO showTimeDTO)
    {
        ShowTimeDTO result =  showtimeService.save(showTimeDTO);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<?> getAll(@RequestParam("page") int page,@RequestParam("limit") int limit)
    {
        Pageable pageable = PageRequest.of(page-1, limit);
        List<ShowTimeDTO> showTimeDTOS = showtimeService.findAll(pageable);
        PageResponse result = new PageResponse();
        result.setResults(showTimeDTOS);
        result.setPage(page);
        result.setLimit(limit);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id)
    {
        if(showtimeService.delete(id))
        {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_GATEWAY);
    }
}
