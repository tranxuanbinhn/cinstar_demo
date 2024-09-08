package com.xb.cinstar.controllers.admin;

import com.xb.cinstar.dto.MovieDTO;
import com.xb.cinstar.dto.OrderDTO;
import com.xb.cinstar.payload.response.PageResponse;
import com.xb.cinstar.pojo.MoviePOJO;
import com.xb.cinstar.pojo.RequestPOJO;
import com.xb.cinstar.pojo.ResultPOJO;
import com.xb.cinstar.service.impl.MovieService;
import com.xb.cinstar.service.impl.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/order")
public class OrderContronllerAdmin {
    @Autowired
    private OrderService orderService;
    @GetMapping()
    public ResponseEntity<?> getAllOrders(@RequestParam("page") int page, @RequestParam("limit") int limit)
    {
        Pageable pageable = PageRequest.of(page-1, limit);
        List<OrderDTO> result = orderService.findAll(pageable);
        PageResponse<OrderDTO> pageResponse = new PageResponse<>();
        pageResponse.setResults(result);
        pageResponse.setPage(page);
        pageResponse.setCounts(orderService.count());
        return new ResponseEntity<>(pageResponse, HttpStatus.OK);
    }
}
