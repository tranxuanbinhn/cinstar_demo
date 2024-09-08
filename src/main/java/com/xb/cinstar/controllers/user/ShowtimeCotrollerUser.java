package com.xb.cinstar.controllers.user;


import com.xb.cinstar.dto.ShowTimeDTO;
import com.xb.cinstar.service.impl.ShowTimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
@CrossOrigin("${allowed.origin}")
@RequestMapping("/api/user/showtime")
@RestController
public class ShowtimeCotrollerUser {
    @Autowired
    private ShowTimeService showTimeService;


    @GetMapping()
    public ResponseEntity<?> getAll(@RequestParam(value = "date", required = false) String date,
                                    @RequestParam(value = "movieid", required = false) Long movieId,
                                    @RequestParam(value = "theaterid", required = false) Long theaterid)
    {
        ShowTimeDTO showTimeDTO = new ShowTimeDTO();


        List<ShowTimeDTO> showTimeDTOS = new ArrayList<>();

        if(theaterid==null&& movieId!=null && date!=null)
        {
            showTimeDTOS = showTimeService.findAll(movieId, date);
        }

        if(theaterid==null&& movieId==null){
            showTimeDTOS = showTimeService.findAll(date);
        }
       if(theaterid!=null&& movieId!=null && date!=null) {
             showTimeDTOS = showTimeService.findAll(date, movieId, theaterid);

        }
        return new ResponseEntity<>(showTimeDTOS, HttpStatus.OK);
    }
    @GetMapping("/getbymovie/{id}")
    public ResponseEntity<?> getAllByMovieId(@PathVariable("id") Long id )
    {
        List<ShowTimeDTO> showTimeDTOS = showTimeService.findByMovieId(id);

        return new ResponseEntity<>(showTimeDTOS, HttpStatus.OK);
    }
    @GetMapping("/getthreebymovie/{id}")
    public ResponseEntity<?> getThreeByMovieId(@PathVariable("id") Long id )
    {
        List<ShowTimeDTO> showTimeDTOS = showTimeService.findThreeByMovieId(id);

        return new ResponseEntity<>(showTimeDTOS, HttpStatus.OK);
    }
    @GetMapping("/getshowtimecurrentday")
    public ResponseEntity<?> getShowtimeCurrentDate()
    {
        List<ShowTimeDTO> showTimeDTOS = showTimeService.findShowtimeFromCurrentDate();

        return new ResponseEntity<>(showTimeDTOS, HttpStatus.OK);
    }
    @GetMapping("/getshowtimefromcurrentdatetotwodate")
    public ResponseEntity<?> getShowtimeCurrentDateToTwoDate()
    {
        List<ShowTimeDTO> showTimeDTOS = showTimeService.findShowTimeFromCurrentDateToTwoDateLater();

        return new ResponseEntity<>(showTimeDTOS, HttpStatus.OK);
    }


}
