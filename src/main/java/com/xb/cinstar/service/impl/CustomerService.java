package com.xb.cinstar.service.impl;

import ch.qos.logback.classic.spi.IThrowableProxy;
import com.xb.cinstar.dto.CustomerDTO;
import com.xb.cinstar.exception.InvalidRequestException;
import com.xb.cinstar.exception.ResourceNotFoundException;

import com.xb.cinstar.models.CustomerModel;
import com.xb.cinstar.models.SeatModel;
import com.xb.cinstar.models.ShowTimeModel;
import com.xb.cinstar.models.TicketModel;
import com.xb.cinstar.repository.ICustomerRespository;
import com.xb.cinstar.repository.ISeatRespository;
import com.xb.cinstar.repository.IShowtimeRespository;
import com.xb.cinstar.repository.ITicketRespository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {
    @Autowired private ICustomerRespository customerRespository;
    @Autowired private IShowtimeRespository showtimeRespository;
    @Autowired private ISeatRespository seatRespository;

    @Autowired private ModelMapper mapper;

    public CustomerDTO save(CustomerDTO customerDTO)
    {
        try{
            CustomerModel customerEntity = mapper.map(customerDTO, CustomerModel.class);
            return  mapper.map(customerRespository.save(customerEntity), CustomerDTO.class);
        }
        catch (ResourceNotFoundException e)
        {
            throw new InvalidRequestException("Invalid Customer");
        }
    }
    public CustomerDTO findOne(Long customerId)
    {
        try{

            Optional<CustomerModel> customerEntity = customerRespository.findById(customerId);
            if(!customerEntity.isPresent())
            {
                throw new ResourceNotFoundException("Not found thí customer");
            }
            return  mapper.map(customerEntity.get(), CustomerDTO.class);
        }
        catch (ResourceNotFoundException e)
        {
            throw new InvalidRequestException("Invalid Customer");
        }
    }






}
