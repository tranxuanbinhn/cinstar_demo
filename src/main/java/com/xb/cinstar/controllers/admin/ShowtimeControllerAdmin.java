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

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/admin/showtime")
public class ShowtimeControllerAdmin {

    @Autowired private ShowTimeService showtimeService;
    @PostMapping
    public ResponseEntity<?> save(@RequestBody ShowTimeDTO showTimeDTO)
    {
        ShowTimeDTO result =  showtimeService.save(showTimeDTO);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    @PostMapping("/many")
    public ResponseEntity<?> saveMany(@RequestBody List<ShowTimeDTO> showTimeDTO)
    {
        List<ShowTimeDTO> result =  showtimeService.saveMany(showTimeDTO);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
//    @DeleteMapping("/all")
//    public ResponseEntity<?> deleteAll()
//    {
//       showtimeService.deleteAll();
//        return new ResponseEntity<>(HttpStatus.OK);
//    }
//    @PostMapping("/many/auto")
//    public ResponseEntity<?> saveManyAuto(@RequestBody List<Long> ids)
//    {
//        String times[]= {"14:00:00", "16:00:00", "18:00:00", "20:00:00", "21:00:00","22:00:00", "23:00:00"};
//        Long screenid = 7L;
//        Long theaterid = 6L;
//
//        LocalDate startDate = LocalDate.now();
//        LocalDate enđate = LocalDate.of(startDate.getYear(), 9, 30);
//        List<ShowTimeDTO> list = new ArrayList<>();
//        for(LocalDate date= startDate; !date.isAfter(enđate); date= date.plusDays(1))
//        {
//            for(Long id:ids)
//            {
//                for(String time:times)
//                {
//                    ShowTimeDTO showTimeDTO = new ShowTimeDTO();
//                    showTimeDTO.setDate(date);
//                    LocalTime localTime = LocalTime.parse(time, DateTimeFormatter.ofPattern("HH:mm:ss"));
//                    showTimeDTO.setTime(localTime);
//                    showTimeDTO.setMovieId(id);
//                    showTimeDTO.setScreenId(7L);
//                    showTimeDTO.setTheaterId(6L);
//                    list.add(showTimeDTO);
//                }
//            }
//        }
//        List<ShowTimeDTO> result = showtimeService.saveMany(list);
//
//        return new ResponseEntity<>(result, HttpStatus.OK);
//    }


    @GetMapping()
    public ResponseEntity<?> getAll(@RequestParam("page") int page,@RequestParam("limit") int limit)
    {
        Pageable pageable = PageRequest.of(page, limit);
        List<ShowTimeDTO> showTimeDTOS = showtimeService.findAll(pageable);
        PageResponse result = new PageResponse();
        result.setResults(showTimeDTOS);
        result.setCounts(showtimeService.count());
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
