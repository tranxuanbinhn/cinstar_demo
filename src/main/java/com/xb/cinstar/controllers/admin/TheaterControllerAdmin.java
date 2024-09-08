package com.xb.cinstar.controllers.admin;

import com.xb.cinstar.dto.TheaterDTO;
import com.xb.cinstar.payload.response.PageResponse;
import com.xb.cinstar.service.impl.SeatService;
import com.xb.cinstar.service.impl.TheaterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/test/theater")
public class TheaterControllerAdmin {

    @Autowired
    private TheaterService theaterService;
    @Autowired private SeatService seatService;
    @PostMapping
    public ResponseEntity<?> save(@RequestBody TheaterDTO theaterDTO)
    {
        TheaterDTO result =  theaterService.save(theaterDTO);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Long id,@RequestBody  TheaterDTO theaterDTO)
    {
        theaterDTO.setId(id);
        TheaterDTO result =  theaterService.save(theaterDTO);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    @GetMapping( )
    public ResponseEntity<?> getAll(@RequestParam("page") int page,@RequestParam("limit") int limit)
    {
        Pageable pageable = PageRequest.of(page-1, limit);
        List<TheaterDTO> theaterDTOS = theaterService.findAll(pageable);
        PageResponse result = new PageResponse();
        result.setResults(theaterDTOS);
        result.setPage(page);
        result.setLimit(limit);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    @GetMapping("/detail/{id}")
    public ResponseEntity<?> getDetail(@PathVariable("id") Long id)
    {

        TheaterDTO theaterDTO = theaterService.findById(id);

        return new ResponseEntity<>(theaterDTO, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id)
    {
        if(theaterService.delete(id))
        {
            return new ResponseEntity<>(HttpStatus.OK);
        }
            return new ResponseEntity<>(HttpStatus.BAD_GATEWAY);
    }


//    @PostMapping("/seat")
//    public ResponseEntity<?> test()
//    {
//        String arr[] = {"A","B","C","D", "E","F","G","H","J","K","L","M","N","O","P","Q"};
//        SeatDTO seatDTO = null;
//        List<SeatDTO> result = new ArrayList<>();
//        for (int i = 0; i < arr.length-1 ; i++)
//        {
//            for (int j = 0; j < 14 ; j++)
//            {
//                 seatDTO = new SeatDTO(arr[i],j+1);
//                seatDTO = seatService.save(seatDTO);
//                result.add(seatDTO);
//            }
//
//
////        }
//
//        return new ResponseEntity<>(result,HttpStatus.OK);
//    }

}
